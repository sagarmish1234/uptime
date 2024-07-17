<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Monitoring Alert</title>
    <style>
        body {
                    font-family: 'Arial', sans-serif;
                    margin: 0;
                    padding: 0;
                    background-color: #f9f9f9;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    height: 100vh;
                }
                .container {
                    background-color: #fff;
                    padding: 30px;
                    border-radius: 10px;
                    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                    max-width: 600px;
                    width: 100%;
                    text-align: center;
                    margin: auto;
                }
                h1 {
                    color: #333;
                    margin-bottom: 20px;
                }
                p {
                    color: #666;
                    margin-bottom: 20px;
                    line-height: 1.6;
                }
                table {
                    width: 100%;
                    border-collapse: collapse;
                    margin-bottom: 20px;
                    margin: auto;
                }
                th, td {
                    padding: 12px;
                    border-bottom: 1px solid #ddd;
                    text-align: left;
                }
                th {
                    background-color: #f2f2f2;
                }
                .down {
                    color: #ff4d4d;
                    font-weight: bold;
                }
                .up {
                    color: #28a745;
                    font-weight: bold;
                }
                .button {
                    display: inline-block;
                    padding: 12px 24px;
                    margin-top: 20px;
                    background-color: #007bff;
                    color: #fff;
                    text-decoration: none;
                    border-radius: 5px;
                    transition: background-color 0.3s, transform 0.3s;
                    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                }
    </style>
</head>
<body>
    <div class="container">
        <h1>Monitoring Alert</h1>
        <p>Dear ${user.firstName},</p>
        <p>The following URL are being monitored. Please review the status :</p>
        <table>
            <thead>
                <tr>
                    <th>URL</th>
                    <th>Status</th>
                    <th>Last Checked</th>
                </tr>
            </thead>
            <tbody>

                    <tr>
                        <td>${url}</td>
                        <td class="down">${status}</td>
                        <td>${lastChecked}</td>
                    </tr>

            </tbody>
        </table>
        <p>Please take appropriate action to resolve the issue as soon as possible.</p>
    </div>
</body>
</html>
