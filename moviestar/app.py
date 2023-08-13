from bson import ObjectId
from pymongo import MongoClient

from flask import Flask, render_template, jsonify, request
from flask.json.provider import JSONProvider

import json
import sys

app = Flask(__name__)

client = MongoClient('localhost', 27017)
db = client.daummovie

# ObjectId 타입으로 되어있는 _id 필드는 Flask의 jsonify 호출시에 문제가 된다.
# 이를 처리하기 위해서 기본 JsonEncoder 가 아닌 custom encoder를 사용한다.
# Custom encoder는 다른 부분은 모두 기본 encoder 에 동작을 위임하고, ObjectId 타입만 직접 처리한다.


class CustomJSONEncoder(json.JSONEncoder):
    def default(self, o):
        if isinstance(o, ObjectId):
            return str(o)
        return json.JSONEncoder.default(self, o)


class CustomJSONProvider(JSONProvider):
    def dumps(self, obj, **kwargs):
        return json.dumps(obj, **kwargs, cls=CustomJSONEncoder)

    def loads(self, s, **kwargs):
        return json.loads(s, **kwargs)


app.json = CustomJSONProvider(app)


@app.route('/')
def home():
    return render_template('index.html')


# 휴지통에 버려지지 않은 영화 목록을 반환한다.
@app.route('/api/list', methods=['GET'])
def show_movies():
    # 클라이언트에서 요청한 정렬 방식이 있는지를 확인한다.
    sortMode = request.args.get('sortMode', 'likes')

    if sortMode == 'likes':
        movies = list(db.movies.find({'trashed': False}, {}))
    else:
        return jsonify({'result': 'failure'})

    # 성공하면 success 메시지와 함께 movie_list 목록을 클라이언트에 전달
    return jsonify({'result': 'success', 'movies_list': movies})


@app.route('/api/like', methods=['POST'])
def like_movie():
    find_id = request.form['id']
    movie = db.movies.find_one({'_id': (ObjectId(find_id))})
    new_likes = movie['likes'] + 1

    result = db.movies.update_one({'_id': (ObjectId(find_id))}, {'$set': {'likes': new_likes}})

    if result.modified_count == 1:
        return jsonify({'result': 'success'})
    else:
        return jsonify({'result': 'failure'})


if __name__ == '__main__':
    print(sys.executable)
    app.run('0.0.0.0', port=5001, debug=True)