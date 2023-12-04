from pymongo import MongoClient

client = MongoClient('localhost', 27017)
db = client.jungle

db.users.delete_many({})