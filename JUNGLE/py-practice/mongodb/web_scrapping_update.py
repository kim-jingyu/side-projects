from pymongo import MongoClient

client = MongoClient('localhost', 27017)
db = client.daummovie

# 헌트 영화의 개봉 년도를 1999년으로 만들기
db.movies.update_one({'title': '헌트'}, {'$set': {'open_year': 1999}})
print(db.movies.find_one({'title': '헌트'}))
