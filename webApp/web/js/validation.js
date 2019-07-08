function phoneValidation() {
    if(!Number(document.getElementById('countryCode').value)){
        showInformationMessage('Error', 'Input correct data');
    }
    else if(!Number(document.getElementById('operatorCode').value)){
        showInformationMessage('Error', 'Input correct data');
    }
    else if(!Number(document.getElementById('number').value)){}
    else if(document.getElementById('number').value.length !== 7){}
    else if(document.getElementById('typeOfPhone').value !== 'mobile' && document.getElementById('typeOfPhone').value !== 'home number'){
        showInformationMessage('Error', 'Input correct data');
    }
    else if(document.getElementById('comment').value.length > 1000){
        showInformationMessage('Error', 'Input correct data');
    }
    else{//if everything is alright
        createPhone();
        hideMyModalForPhone();
    }
}

function contactValidation() {
    if(document.getElementById('nameOfContact').value.length === 0){
        showInformationMessage('Error', 'Input correct data');
    }
    else if(document.getElementById('surnameOfContact').value.length === 0){
        showInformationMessage('Error', 'Input correct data');

    }
    else if(document.getElementById('fnameOfContact').value.length === 0){
        showInformationMessage('Error', 'Input correct data');
    }
    else if(!checkBirthday('bthOfContact')){
        showInformationMessage('Error', 'Input correct data');
    }
    else if(document.getElementById('sex').value !== 'man' && document.getElementById('sex').value !== 'woman'){
        showInformationMessage('Error', 'Input correct data');
    }
    else if(document.getElementById('ctzOfContact').value.length === 0){
        showInformationMessage('Error', 'Input correct data');
    }
    else if(document.getElementById('fstatOfContact').value !== 'married' && document.getElementById('fstatOfContact').value !== 'not married' && document.getElementById('fstatOfContact').value !== 'diverced'){
        showInformationMessage('Error', 'Input correct data');
    }
    else if(document.getElementById('siteOfContact').value.length === 0){
        showInformationMessage('Error', 'Input correct data');
    }
    else if(!checkEmail()){
        showInformationMessage('Error', 'Input correct data');
    }
    else if(document.getElementById('workOfContact').value.length === 0){
        showInformationMessage('Error', 'Input correct data');
    }
    else if(document.getElementById('countryOfContact').value.length === 0){
        showInformationMessage('Error', 'Input correct data');
    }
    else if(document.getElementById('cityOfContact').value.length === 0){
        showInformationMessage('Error', 'Input correct data');
    }
    else if(document.getElementById('streetOfContact').value.length === 0){
        showInformationMessage('Error', 'Input correct data');
    }
    else if(document.getElementById('homeOfContact').value.length === 0){
        showInformationMessage('Error', 'Input correct data');
    }
    else if(document.getElementById('codeOfContact').value.length === 0){
        showInformationMessage('Error', 'Input correct data');
    }
    else{
        doContact();//if everything is alright
    }
}

function sendEmailValidation() {
    if(document.getElementById('emHead').value.length === 0){
        showInformationMessage('Error', 'Input correct data');
    }
    else {
        sendEmail();//if everything is alright
    }
}

function searchContactValidation() {
    if(document.getElementById('dirBirth').value !== 'less' && document.getElementById('dirBirth').value !== 'more'){
        showInformationMessage('Error', 'Input correct data');
    }
    else if(document.getElementById('fstatOfContactSearch').value !== 'all' && document.getElementById('fstatOfContactSearch').value !== 'married' && document.getElementById('fstatOfContactSearch').value !== 'not married' && document.getElementById('fstatOfContactSearch').value !== 'diverced'){
        showInformationMessage('Error', 'Input correct data');
    }
    else if(document.getElementById('sexSearch').value !== 'all' && document.getElementById('sexSearch').value !== 'man' && document.getElementById('sexSearch').value !== 'woman'){
        showInformationMessage('Error', 'Input correct data');
    }
    else if(document.getElementById('bthOfContactSearch').value.length !== 0){
        if(!checkBirthday('bthOfContactSearch')){
            showInformationMessage('Error', 'Input correct data');
        }
        else{
            searchContact();//if everything is alright
        }
    }
    else{
        searchContact();//if everything is alright
    }
}

function checkEmail() {
    if(document.getElementById('emOfContact').value.length === 0)
        return false;
    if(!document.getElementById('emOfContact').value.includes('@'))
        return false;
    return true;
}

function checkBirthday(id) {
    if(document.getElementById(id).value.length === 0)//проверили что поле не пустое
        return false;
    var date = document.getElementById(id).value.split('-');
    if(date.length !== 3)//проверили что есть 3 значения разделённые точками
        return false;
    for(var i = 0; i < 3; i++){//проверили что все они числа и все >= 0
        if(!Number(date[i]))
            return false;
        else if(Number(date[i]) < 0)
            return false;
    }
    if(!(date[1] >= 1 && date[1] <= 12))
        return false;
    if(!(date[2] >= 1 && date[2] <= 31))
        return false;
    return true;
}

function attachmentValidation() {
    if(!editing && !document.getElementById('attach').files[0]){
        showInformationMessage('Error', 'Input correct data');
    }
    else {
        createAttachment();//if everything is alright
        hideMyModalForAttachment();
    }
}