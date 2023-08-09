from pymongo import MongoClient

client = MongoClient('localhost', 27017)
db = client.jungle

db.users.update_one({'name': 'kim'}, {'$set': {'age': 79}})

user_kim = db.users.find_one({'name': 'kim'})
print('user_kim:',user_kim)
