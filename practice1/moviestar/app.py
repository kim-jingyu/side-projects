from pymongo import MongoClient
from flask import Flask, request, render_template, jsonify
from flask.json.provider import JSONProvider

from bson import ObjectId
import json

app = Flask(__name__)

client = MongoClient('mongodb://test:test@localhost', 27017)
db = client.dbjungle


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


@app.route('/movie/list', methods=['GET'])
def show_movies():
    sort_mode = request.args.get('sortMode', 'likes')

    movies = []
    if sort_mode == 'likes':
        movies = list(db.movies.find({'trashed': False}).sort('likes', -1))
    elif sort_mode == 'viewers':
        movies = list(db.movies.find({'trashed': False}).sort('audience', -1))
    elif sort_mode == 'date':
        movies = list(db.movies.find({'trashed': False}).sort([('open_year', -1), ('open_month', -1), ('open_day', -1)]))
    else:
        return jsonify({'result': 'failure'})

    return jsonify({'result': 'success', 'msg': '영화 리스트 GET 요청 완료!', 'movies_list': movies})


@app.route('/movie/trash', methods=['GET'])
def show_trashed_movies():
    sort_mode = request.args.get('sortMode', 'likes')

    movies = []
    if sort_mode == 'likes':
        movies = list(db.movies.find({'trashed': True}).sort('likes', -1))
    elif sort_mode == 'viewers':
        movies = list(db.movies.find({'trashed': True}).sort('audience', -1))
    elif sort_mode == 'date':
        movies = list(db.movies.find({'trashed': True}).sort([('open_year', -1), ('open_month', -1), ('open_day', -1)]))
    else:
        return jsonify({'result': 'failure'})

    return jsonify({'result': 'success', 'msg': '영화 리스트 GET 요청 완료!', 'movies_list': movies})


@app.route('/movie/trash', methods=['POST'])
def to_trashed_movie():
    find_id = request.form['id']

    result = db.movies.update_one({'_id': ObjectId(find_id)}, {'$set': {'trashed': True}})

    print(result.modified_count)
    return jsonify({'result': 'success', 'msg': db.movies.find_one({'_id': ObjectId(find_id)})['title'] + ' 휴지통 보내기 완료!'})


@app.route('/movie/like', methods=['POST'])
def like_movie():
    find_id = request.form['id']
    movie = db.movies.find_one({'_id': ObjectId(find_id)})
    new_likes = movie['likes'] + 1

    db.movies.update_one({'_id': ObjectId(find_id)}, {'$set': {'likes': new_likes}})

    return jsonify({'result': 'success', 'msg': movie['title'] + ' 좋아요 완료!'})


@app.route('/movie/trash/restore', methods=['POST'])
def restore_trashed_movie():
    find_id = request.form['id']
    movie = db.movies.find_one({'_id': ObjectId(find_id)})

    db.movies.update_one({'_id': ObjectId(find_id)}, {'$set': {'trashed': False}})

    return jsonify({'result': 'success', 'msg': movie['title'] + ' 복구 완료!'})


@app.route('/movie/trash', methods=['DELETE'])
def delete_trashed_movie():
    find_id = request.form['id']
    movie_title = db.movies.find_one({'_id': ObjectId(find_id)})['title']

    db.movies.delete_one({'_id': ObjectId(find_id)})

    return jsonify({'result': 'success', 'msg': movie_title + ' 영구 삭제 완료!'})


if __name__ == '__main__':
    app.run('0.0.0.0', 5000, debug=True)