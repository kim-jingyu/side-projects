from flask import Flask

app = Flask(__name__)


@app.route('/')
def home():
    return 'THIS IS HOME!'

@app.route('/mypage')
def mypage():
    return 'THIS IS MY PAGE!'


if __name__ == '__main__':
    app.run('0.0.0.0', 5001, debug=True)
