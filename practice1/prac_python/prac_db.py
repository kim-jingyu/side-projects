from pymongo import MongoClient
client = MongoClient('localhost', 27017)
db = client.jungle

minios_open_month = db.movies.find_one({'title': '미니언즈2'})['open_month']

target_movies = list(db.movies.find({'open_month': minios_open_month}))
for target_movie in target_movies:
    print(target_movie['title'])

db.movies.update_one({'title': '헌트'}, {'$set': {'open_year': 1999}})
