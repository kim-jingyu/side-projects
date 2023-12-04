from pymongo import MongoClient

client = MongoClient('localhost', 27017)
db = client.daummovie

# 영화제목 '미니언즈2'의 개봉월 가져오기
movie_minions = db.movies.find_one({'title': '미니언즈2'})
minions_open_month = movie_minions['open_month']
print(minions_open_month)

# '미니언즈2'와 같은 월에 개봉한 영화 제목들 가져오기
movies_same_month_to_minions = list(db.movies.find({'open_month': minions_open_month}))
for movie in movies_same_month_to_minions:
    print(movie['title'])
