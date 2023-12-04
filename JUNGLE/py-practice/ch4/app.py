from flask import Flask, render_template, request, jsonify

app = Flask(__name__)


@app.route('/')
def home():
    return render_template('index.html')

@app.route('/mypage')
def mypage():
    return 'THIS IS MY PAGE!'

@app.route('/test', methods=['GET'])
def test_get():
    title_receive = request.args.get('title_give')
    print('get 요청에 대한 응답:', title_receive)
    return jsonify({'result': 'success', 'msg': '이 요청은 GET 요청입니다.'})

@app.route('/test', methods=['POST'])
def test_post():
    title_receive = request.form['title_give']
    print('post 요청에 대한 응답:', title_receive)
    return jsonify({'result': 'success', 'msg': '이 요청은 POST 요청입니다.'})


if __name__ == '__main__':
    app.run('0.0.0.0', 5001, debug=True)
