window.addEventListener('load', function () {
    var image = document.getElementById('contactPhoto');
    var upload = document.getElementById('uploadPhoto');

    image.onclick = function () {
        upload.click();
    };

    document.getElementById('btnAllContacts').addEventListener('click', function(){ids = null;displayContacts(1);});
    document.getElementById('btnNewContact').addEventListener('click', function(){hideIndexPage();newConatct = 1;showContactPage(0);});
    document.getElementById('btnDeleteContacts').addEventListener('click', function(){if(getSelectedItems('check').length !== 0){showMyDangerContactModal();}});
    document.getElementById('btnSearchForm').addEventListener('click', function(){hideGroupOfMainButtons(); showSearchPage();});
    document.getElementById('btnSendEmail').addEventListener('click', beforeSend);
    document.getElementById('btnDeletePhones').addEventListener('click', function(){if(getSelectedItems('checkPhone').length !== 0){showMyDangerPhoneModal();}});
    document.getElementById('btnCreatePhone').addEventListener('click', showMyModalForPhone);
    document.getElementById('btnDeleteAttachments').addEventListener('click', function(){if(getSelectedItems('checkAttachment').length !== 0){showMyDangerAttachmentModal();}});
    document.getElementById('btnAddAttachments').addEventListener('click', showMyModalForAttachment);
    document.getElementById('btnSaveAll').addEventListener('click', contactValidation);
    document.getElementById('btnBackFromContactPage').addEventListener('click', function(){hideContactPage();showIndexPage();});
    document.getElementById('btnBackFromContactPage2').addEventListener('click', function(){hideContactPage();showIndexPage();});
    document.getElementById('btnCancelForAttachment').addEventListener('click', hideMyModalForAttachment);
    document.getElementById('btnSaveForAttachment').addEventListener('click', attachmentValidation);
    document.getElementById('btnCancelForPhone').addEventListener('click', hideMyModalForPhone);
    document.getElementById('btnSaveForPhone').addEventListener('click', phoneValidation);
    document.getElementById('btnConfirmForDeletePhones').addEventListener('click', deletePhones);
    document.getElementById('btnConfirmForDeleteContacts').addEventListener('click', deleteContacts);
    document.getElementById('btnConfirmForDeleteAttachments').addEventListener('click', deleteAttachments);
    document.getElementById('btnRejectForDeleteAttachments').addEventListener('click', hideMyDangerAttachmentModal);
    document.getElementById('btnRejectForDeleteContacts').addEventListener('click', hideMyDangerContactModal);
    document.getElementById('btnRejectForDeletePhones').addEventListener('click', hideMyDangerPhoneModal);
    document.getElementById('btnOkForWarningContact').addEventListener('click', hideInformationMessage);
    document.getElementById('btnSearchContact').addEventListener('click', searchContactValidation);
    document.getElementById('btnBackFromSearchPage').addEventListener('click', function(){hideSearchPage();showGroupOfMainButtons();});
    document.getElementById('btnBackFromEmailPage').addEventListener('click', function(){hideEmailPage(false);showIndexPage();});
    document.getElementById('btnSendEmailForContacts').addEventListener('click', sendEmailValidation);

    document.getElementById('mainChoose').addEventListener('click', chooseAll);
    document.getElementById('uploadPhoto').addEventListener('change', changePhoto);
    document.getElementById('paginationNum').addEventListener('change', changePagination);
    document.getElementById('template').addEventListener('change', myTemplate);
});
var contactsNum;
var ids = null;
var contactsPerPage = 5;
function displayContacts(pageNumber) {
    if(ids === null) {
        fetch('api/contacts?pageNumber=' + pageNumber + '&contactsPerPage=' + contactsPerPage).then(function (res) {
            if(res.status !== 200){
                showInformationMessage('Error', 'Could not display contacts');
                return null;
            }
            else {
                return res.json();
            }
        }).then(function (peopleAndNum) {
            contactsNum = peopleAndNum.contactsNum;
            var people = peopleAndNum.people;
            var output = '';
            var pattern = '<tr><th scope="row"><input type="checkbox" class="form-check-input" name="check" id="{{id}}"></th>' +
                '<td><a href="#" onclick="hideIndexPage(); newConatct = 0; fillContact({{id}})">{{surName}} {{name}} {{fathersName}}</a></td>' +
                '<td>{{birthdate}}</td><td>{{address.country}}, {{address.city}}, {{address.street}} {{address.homeNum}}, {{address.postcode}}</td>' +
                '<td>{{currentWorkspase}}</td></tr>';
            people.forEach(function (person) {
                output += Mustache.render(pattern, person);
            });
            document.getElementById('mainChoose').checked = false;
            document.getElementById('persons').innerHTML = output;
            pagination(contactsPerPage, contactsNum);
        }).catch(function (err) {
            console.log(err);
        });
    }
    else{
        contactsNum = ids.length;
        ids = ids.splice((pageNumber-1)*contactsPerPage, contactsPerPage);
        fetch('api/contacts', {
            method: 'POST',
            headers: {
                'content-type': 'application/json'
            },
            body: JSON.stringify(ids)
        }).then(function (res) {
            if(res.status !== 200){
                showInformationMessage('Error', 'Could not display contacts');
                return null;
            }
            else {
                return res.json();
            }
        }).then(function (people) {
            var output = '';
            var pattern = '<tr><th scope="row"><input type="checkbox" class="form-check-input" name="check" id="{{id}}"></th>' +
                '<td><a href="#" onclick="hideIndexPage(); newConatct = 0; fillContact({{id}})">{{surName}} {{name}} {{fathersName}}</a></td>' +
                '<td>{{birthdate}}</td><td>{{address.country}}, {{address.city}}, {{address.street}} {{address.homeNum}}, {{address.postcode}}</td>' +
                '<td>{{currentWorkspase}}</td></tr>';
            people.forEach(function (person) {
                output += Mustache.render(pattern, person);
            });
            document.getElementById('mainChoose').checked = false;
            document.getElementById('persons').innerHTML = output;
            pagination(contactsPerPage, contactsNum);
        }).catch(function (err) {
            console.log(err);
        });
    }
}

function getSelectedItems(name) {
    var checks = document.getElementsByName(name);
    var ids = [];
    for (var i = 0; i < checks.length; i++) {
        if (checks.item(i).checked) {
            ids[ids.length] = Number(checks.item(i).getAttribute('id'));
        }
    }
    return ids;
}

function deleteContacts() {
    hideMyDangerContactModal();
    var ids = getSelectedItems('check');
    if (ids.length !== 0) {
        fetch('api/contacts', {
            method: 'DELETE',
            headers: {
                'content-type': 'application/json; charset=UTF-8'
            },
            body: JSON.stringify(ids)
        }).then(function (res) {
            if (res.status === 200) {
                showInformationMessage('Information', 'Selected contacts have been deleted');
                contactsNum -= ids.length;
                displayContacts(1);
            }
            else {
                showInformationMessage('Error', 'Contacts have not been deleted');
            }
        });
    }
}

function chooseAll() {
    var checks = document.getElementsByName('check');
    if(document.getElementById('mainChoose').checked) {
        for (var i = 0; i < checks.length; i++){
            checks.item(i).checked = true;
        }
    }
    else{
        for (var i = 0; i < checks.length; i++){
            checks.item(i).checked = false;
        }
    }
}

function beforeSend() {
    var ids = getSelectedItems('check');
    fillEmails(ids);
    hideIndexPage();
    showEmailPage();
}