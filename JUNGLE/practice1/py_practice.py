def question1():
    fruits = ['사과', '배', '배', '감', '수박', '귤', '딸기', '사과', '배', '수박']

    def count_fruits(fruit_name):
        count = 0
        for i in range(len(fruits)):
            if fruits[i] == fruit_name:
                count += 1
        return count

    print('사과의 갯수 :', count_fruits('사과'))
    print('수박의 갯수 :', count_fruits('수박'))
    print('배의 갯수 :', count_fruits('배'))


def question2():
    people = [{'name': 'bob', 'age': 20},
              {'name': 'carry', 'age': 38},
              {'name': 'john', 'age': 7},
              {'name': 'smith', 'age': 17},
              {'name': 'ben', 'age': 27}]

    def get_age(name):
        for person in people:
            if person['name'] == name:
                return person['age']
        return '해당하는 이름이 없습니다.'

    print('bob의 나이:', get_age('bob'))


if __name__ == '__main__':
    question2()
