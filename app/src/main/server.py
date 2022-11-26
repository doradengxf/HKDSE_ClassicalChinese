import flask
from flask import request
import sqlite3

app = flask.Flask(__name__)
#app.config["DEBUG"] = True # Enable debug mode to enable hot-reloader.

@app.route('/scheduleadd', methods=['GET'])
def tutorial():
    date = request.args.get('date', '')
    data = request.args.get('data', '')

    con = sqlite3.connect('my-db.db')
    if (len(data) > 0 and len(date) > 0):
    # insert into db
        insertQuery = "INSERT INTO SCHEDULE (RELATEDTO, TODO, DATE) values (?,?,?);" 
        con.execute(insertQuery, ("1", data, date))
        con.commit()

    # select from db
    cursor = con.execute('SELECT TODO,DATE from SCHEDULE WHERE RELATEDTO="1";')
    toDoList = []
    schedule = []
    date = []
    for row in cursor:
        toDoList.append(row) 
    con.close()
    for i in range(0, len(toDoList)):
        schedule.append(toDoList[i][0])
        date.append(toDoList[i][1])
    outdata = { 
         "Schedule": schedule,
         "Date": date
        }
    return outdata


@app.route('/schedule', methods=['GET'])
def schedule():
    userID = request.args.get('UID', '')

    con = sqlite3.connect('my-db.db')

    # select from db
    cursor = con.execute('SELECT TODO,DATE from SCHEDULE WHERE RELATEDTO="'+userID+'";')
    toDoList = []
    schedule = []
    date = []
    for row in cursor:
        print(row[0])
        toDoList.append(row) 
    con.close()
    for i in range(0, len(toDoList)):
        schedule.append(toDoList[i][0])
        date.append(toDoList[i][1])

    outdata = { 
         "Schedule": schedule,
         "Date": date
        }
    return outdata




@app.route('/articles', methods=['GET'])





# adds host="0.0.0.0" to make the server publicly available 
# 
app.run(host="0.0.0.0",port = 5050)