from flask import Flask, render_template, request, send_file
import cx_Oracle
import csv
from io import StringIO

app = Flask(__name__)

# Oracle database connection parameters
oracle_username = 'your_username'
oracle_password = 'your_password'
oracle_dsn = 'your_dsn'  # e.g., 'localhost:1521/XE'

@app.route('/')
def index():
    # Get the list of columns from the database table
    columns = get_table_columns('your_table_name')
    return render_template('index.html', columns=columns)

@app.route('/fetch_data', methods=['POST'])
def fetch_data():
    selected_columns = request.form.getlist('columns')
    
    # Fetch data from the database based on selected columns
    data = get_data('your_table_name', selected_columns)

    # Render the data on the webpage
    return render_template('data.html', data=data)

@app.route('/download_csv', methods=['POST'])
def download_csv():
    selected_columns = request.form.getlist('columns')

    # Fetch data from the database based on selected columns
    data = get_data('your_table_name', selected_columns)

    # Create a CSV file in memory
    csv_data = StringIO()
    csv_writer = csv.writer(csv_data)
    csv_writer.writerow(selected_columns)  # Write header
    csv_writer.writerows(data)

    # Return the CSV file as a downloadable file
    return send_file(csv_data, mimetype='text/csv', attachment_filename='data.csv', as_attachment=True)

def get_table_columns(table_name):
    # Connect to Oracle database and retrieve column names
    with cx_Oracle.connect(oracle_username, oracle_password, oracle_dsn) as connection:
        cursor = connection.cursor()
        cursor.execute(f"SELECT column_name FROM all_tab_columns WHERE table_name = '{table_name}'")
        columns = [row[0] for row in cursor.fetchall()]
    return columns

def get_data(table_name, selected_columns):
    # Connect to Oracle database and fetch data based on selected columns
    with cx_Oracle.connect(oracle_username, oracle_password, oracle_dsn) as connection:
        cursor = connection.cursor()
        selected_columns_str = ', '.join(selected_columns)
        query = f"SELECT {selected_columns_str} FROM {table_name} WHERE ROWNUM <= 20"
        cursor.execute(query)
        data = cursor.fetchall()
    return data

if __name__ == '__main__':
    app.run(debug=True)
