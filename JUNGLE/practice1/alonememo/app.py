from flask import Flask, request, render_template, jsonify
from pymongo import MongoClient
import requests
from bs4 import BeautifulSoup

app = Flask(__name__)

client = MongoClient('localhost', 27017)
db = client.jungle


@app.route('/')
def home():
    return render_template('index.html')


@app.route('/memo', methods=["GET"])
def show_articles():
    articles = list(db.articles.find({}, {'_id': False}))
    return jsonify({'result': 'success', 'msg': 'GET 연결 완료.', 'articles': articles})


@app.route('/memo', methods=["POST"])
def post_article():
    url_receive = request.form['url_give']
    comment_receive = request.form['comment_give']

    headers = {
        'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36'}
    data = requests.get(url_receive, headers=headers)
    soup = BeautifulSoup(data.text, 'html.parser')

    og_title = soup.select_one('meta[property="og:title"]')
    if not og_title:
        return jsonify({'result': 'fail'})
    title = og_title['content']

    og_image = soup.select_one('meta[property="og:image"]')
    if not og_image:
        return jsonify({'result': 'fail'})
    image = og_image['content']

    og_description = soup.select_one('meta[property="og:description"]')
    if not og_description:
        return jsonify({'result': 'fail'})
    description = og_description['content']

    doc = {
        'url': url_receive,
        'title': title,
        'image': image,
        'description': description,
        'comment': comment_receive
    }

    db.articles.insert_one(doc)

    return jsonify({'result': 'success', 'msg': 'POST 연결 완료'})


if __name__ == '__main__':
    app.run('0.0.0.0', 5001, debug=True)
