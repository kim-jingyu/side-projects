from flask import Flask, render_template, jsonify, request
app = Flask(__name__)

import requests
from bs4 import BeautifulSoup

from pymongo import MongoClient

client = MongoClient('mongodb://test:test@localhost', 27017)
db = client.alonememo

# HTML을 주는 부분
@app.route('/')
def home():
    return render_template('index.html')

# 포스팅 정보 리스트 나열
@app.route('/memo', methods=['GET'])
def listing():
    # 1. 모든 document 찾기 + _id 값은 출력에서 제외하기
    # mongoDB에서 _id 값을 제외한 모든 데이터 조회해오기
    result = list(db.articles.find({}, {'_id': False}))

    # 2. articles 라는 키 값으로 영화정보 내려주기
    return jsonify({'result': 'success', 'msg': 'GET 연결되었습니다.', 'articles': result})

# API 역할을 하는 부분
# param : URL(url_give), comment(comment_give)
# return : url, title, description, 이미지URL(image), comment
@app.route('/memo', methods=['POST'])
def saving():
    # 1. 클라이언트로부터 데이터를 받기
    url_receive = request.form['url_give']
    comment_receive = request.form['comment_give']

    # 2. meta tag를 스크래핑하기
    headers = {'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36'}
    data = requests.get(url_receive, headers=headers)
    soup = BeautifulSoup(data.text, 'html.parser')

    og_image = soup.select_one('meta[property="og:image"]')
    og_title = soup.select_one('meta[property="og:title"]')
    og_description = soup.select_one('meta[property="og:description"]')

    url_image = og_image['content']
    url_title = og_title['content']
    url_description = og_description['content']

    article = {'url': url_receive,
               'title': url_title,
               'desc': url_description,
               'image': url_image,
               'comment': comment_receive}

    # 3. mongoDB에 데이터 넣기
    db.articles.insert_one(article)

    return jsonify({'result': 'success', 'msg': 'POST 연결되었습니다.'})


if __name__ == '__main__':
    app.run('0.0.0.0', port=5001, debug=True)