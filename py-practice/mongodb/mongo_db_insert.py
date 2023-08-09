from pymongo import MongoClient

client = MongoClient('localhost', 27017)
db = client.jungle

db.users.insert_one({'name': 'kim', 'age': 10})
db.users.insert_one({'name': 'lee', 'age': 20})
db.users.insert_one({'name': 'park', 'age': 30})
