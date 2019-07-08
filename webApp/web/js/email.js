function sendEmail() {
    var emHead = document.getElementById('emHead').value;
    var emBody = document.getElementById('emBody').value;

    var ids = getSelectedItems('check');

    if(ids.length !== 0) {
        fetch('api/email', {
            method: 'POST',
            headers: {
                'content-type': 'application/json; charset=UTF-8'
            },
            body: JSON.stringify({ids: ids, header: emHead, message: emBody})
        }).then(function (res) {
            if (res.status === 200) {
                showInformationMessage('Information', 'A message has been sent to selected contacts');
            }
            else {
                showInformationMessage('Error', 'Message has not been sent');
            }
            hideEmailPage(true);
            showIndexPage();
        });
    }
    else {
        showInformationMessage('Warning', 'Select recipients');
    }
}

function myTemplate() {
    if(document.getElementById('template').value === 'Greeting'){
        document.getElementById('emBody').value = 'Our congratulations, dear <name> <fname> <surname>!';
        document.getElementById('emHead').value = 'Greeting';
    }
    else if(document.getElementById('template').value === 'Invitation'){
        document.getElementById('emBody').value = 'Dear <name> <fname> <surname>,\nwe are glad to tell you that you are invited to our meeting!';
        document.getElementById('emHead').value = 'Invitation';

    }
    else if(document.getElementById('template').value === 'Notification'){
        document.getElementById('emBody').value = 'Mr. <name> <fname> <surname>,\nwe want to remind you that you are invited to the meeting!';
        document.getElementById('emHead').value = 'Notification';

    }
}

function fillEmails(ids) {
    if(ids.length !== 0) {
        fetch('api/emails', {
            method: 'POST',
            headers: {
                'content-type': 'application/json; charset=UTF-8'
            },
            body: JSON.stringify(ids)
        }).then(function (res) {
            return res.json();
        }).then(function (emails) {
            var emailsStr = '';
            emails.forEach(function (email) {
                emailsStr += email + '\n';
            });
            document.getElementById('recipients').value = emailsStr;
        });
    }
}