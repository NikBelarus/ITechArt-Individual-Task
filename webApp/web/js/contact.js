var flPhoto=false;
var newConatct = 0;

function getStandPhoto() {
    fetch('api/photo').then(function (res) {
        return res.json();
    }).then(function (photo) {
        document.getElementById('contactPhoto').src = photo;
    }).catch(function (err) {
        console.log(err);
    });
}

function fillContact(id) {
    fetch('api/contact?id=' + id).then(function (res) {
        var status = res.status;
        if(status !== 200){
            showInformationMessage('Error', 'Could not get contact information with id ' + id);
            return null;
        }
        else {
            return res.json();
        }
    }).then(function (person) {
        document.getElementById('hiddenIdOfContact').value = person.id;
        document.getElementById('nameOfContact').value = person.name;
        document.getElementById('surnameOfContact').value = person.surName;
        document.getElementById('fnameOfContact').value = person.fathersName;
        document.getElementById('bthOfContact').value = person.birthdate;
        if(person.sex === 'man'){
            document.getElementById('sex')[0].selected = true;
        }
        else{
            document.getElementById('sex')[1].selected = true;
        }
        document.getElementById('ctzOfContact').value = person.citizenship;
        if(person.familyStatus === 'married'){
            document.getElementById('fstatOfContact')[0].selected = true;
        }
        else if(person.familyStatus === 'not married'){
            document.getElementById('fstatOfContact')[1].selected = true;
        }
        else{
            document.getElementById('fstatOfContact')[2].selected = true;
        }
        document.getElementById('siteOfContact').value = person.webSite;
        document.getElementById('emOfContact').value = person.email;
        document.getElementById('workOfContact').value = person.currentWorkspase;
        document.getElementById('countryOfContact').value = person.address.country;
        document.getElementById('cityOfContact').value = person.address.city;
        document.getElementById('streetOfContact').value = person.address.street;
        document.getElementById('homeOfContact').value = person.address.homeNum;
        document.getElementById('appOfContact').value = person.address.apartment;
        document.getElementById('codeOfContact').value = person.address.postcode;
        document.getElementById('contactPhoto').src = person.photo;

        for(var i = 0; i < person.phones.length; i++){
            var phone = {};
            phone.id = Number(person.phones[i].id);
            phone.countryCode = Number(person.phones[i].countryCode);
            phone.operatorCode = Number(person.phones[i].operatorCode);
            phone.number = person.phones[i].number;
            phone.type = person.phones[i].type;
            phone.comment = person.phones[i].comment;
            phones.addPhone(phone);
        }
        for(var j = 0; j < person.attachments.length; j++){
            var attachment = {};
            attachment.id = Number(person.attachments[j].id);
            attachment.name = person.attachments[j].name;
            attachment.date = person.attachments[j].date;
            attachment.comment = person.attachments[j].comment;
            attachment.vis = 'visible';
            attachments.addAttachment(attachment);
        }
        showPhones();
        showAttachments();
        showContactPage(1);
    }).catch(function (err) {
        console.log(err);
    });
}

function doContact() {
    var id = 0;
    if(newConatct === 1){
        contactsNum++;
    }
    else {
        id = document.getElementById('hiddenIdOfContact').value;
    }
    var name = document.getElementById('nameOfContact').value;
    var surName = document.getElementById('surnameOfContact').value;
    var fathersName = document.getElementById('fnameOfContact').value;
    var birthdate = document.getElementById('bthOfContact').value;
    var sex = document.getElementById('sex').value;
    var citizenship = document.getElementById('ctzOfContact').value;
    var familyStatus = document.getElementById('fstatOfContact').value;
    var webSite = document.getElementById('siteOfContact').value;
    var email = document.getElementById('emOfContact').value;
    var currentWorkspase = document.getElementById('workOfContact').value;
    var country = document.getElementById('countryOfContact').value;
    var city = document.getElementById('cityOfContact').value;
    var street = document.getElementById('streetOfContact').value;
    var homeNum = document.getElementById('homeOfContact').value;
    var apartment = document.getElementById('appOfContact').value;
    var postcode = document.getElementById('codeOfContact').value;
    var photo = null;
    if(flPhoto) {
        photo = document.getElementById('contactPhoto').src;
    }

    var str = {id:id, name:name, surName:surName, fathersName:fathersName, birthdate:birthdate,
        sex:sex, citizenship:citizenship, familyStatus:familyStatus, webSite:webSite, email:email,
        currentWorkspase:currentWorkspase, photo:photo, address:{country:country, city:city, street:street, homeNum:homeNum,
            apartment:apartment, postcode:postcode}, phones:phones.getPhones(), attachments:attachments.getAttachments()};

    //--------------------------------------------------------------------
    document.getElementById('contact').value = JSON.stringify(str);
    var formData = new FormData();
    var form = document.getElementById('formOfContact');
    formData.append(form.childNodes[1].name, form.childNodes[1].value);
    for(var i = 3; i < form.childNodes.length; i++){
        var n = attachments.getAttachmentByID(Number(form.childNodes[i].name)).name;
        var type = n.split('.');
        formData.append(n, form.childNodes[i].files[0], form.childNodes[i].name + '.' + type[1]);
    }
    //--------------------------------------------------------------------
    var urlFetch = 'api/contact';
    var method = 'POST';
    if(newConatct === 0){
        method = 'PUT';
    }
    fetch(urlFetch, {
        method: method,
        body: formData
    }).then(function (res) {
        var status = res.status;
        if(status === 200){
            showInformationMessage('Information', 'Information saved successfully');
            hideContactPage();
            showIndexPage();
            displayContacts(1);
        }
        else if(status === 400){
            showInformationMessage('Error', 'Contact with such email or website already exists');
        }
        else if(status === 403){
            showInformationMessage('Error', 'Information not saved');
        }
        else if(status === 406){
            showInformationMessage('Error', 'The information is not valid');
        }
        else {
            showInformationMessage('Error', 'Server error');
        }
    }).catch(function (err) {
        console.log(err);
    });
}

function changePhoto() {
    var preview = document.getElementById('contactPhoto');
    var file = document.querySelector('input[type=file]').files[0];
    var fileReader = new FileReader();

    fileReader.onloadend = function () {
        preview.src = fileReader.result;
        flPhoto=true;
    };

    if(file){
        fileReader.readAsDataURL(file);
    }
}