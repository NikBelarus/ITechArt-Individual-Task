var phones = new Phones();

function Phones(){
    this.phones = [];
    this.lastId = 1;

    this.getPhones = function () {
        return this.phones;
    };

    this.addPhone = function (phone) {
        this.phones[this.phones.length] = phone;
    };

    this.myDelete = function (ids) {
        for(var i = 0; i < ids.length; i++){
            for(var j = 0; j < this.phones.length; j++){
                if(this.phones[j].id === ids[i]){
                    this.phones.splice(j, 1);
                }
            }
        }
    };

    this.getLastId = function () {
        this.lastId -= 1;
        return this.lastId;
    };

    this.getPhoneByID = function (id) {
        for(var i = 0; i < this.phones.length; i++){
            if(this.phones[i].id === id){
                return this.phones[i];
            }
        }
    };

    this.edit = function (id) {
        var temp = phones.getPhoneByID(id);
        temp.countryCode = document.getElementById('countryCode').value;
        temp.operatorCode = document.getElementById('operatorCode').value;
        temp.number = document.getElementById('number').value;
        temp.type = document.getElementById('typeOfPhone').value;
        temp.comment = document.getElementById('comment').value;
    }
}

function deletePhones() {
    var ids = getSelectedItems('checkPhone');
    phones.myDelete(ids);
    hideMyDangerPhoneModal();
    showPhones();
}

function showPhones() {
    var pattern = '{{#phones}}<tr><th scope="row"><input type="checkbox" class="form-check-input" name="checkPhone" id="{{id}}"></th>' +
        '<td><a href="#" onclick="fillPhone({{id}})">+{{countryCode}}{{operatorCode}}{{number}}</a></td><td>{{type}}</td><td>{{comment}}</td></tr>{{/phones}}';
    var output = Mustache.render(pattern, phones);
    document.getElementById('phones').innerHTML = output;
}

function fillPhone(id) {
    var tempPhone = phones.getPhoneByID(id);
    document.getElementById('countryCode').value = tempPhone.countryCode;
    document.getElementById('operatorCode').value = tempPhone.operatorCode;
    document.getElementById('number').value = tempPhone.number;
    if(tempPhone.type === 'mobile'){
        document.getElementById('typeOfPhone')[0].selected = true;
    }
    else{
        document.getElementById('typeOfPhone')[1].selected = true;
    }
    document.getElementById('comment').value = tempPhone.comment;
    document.getElementById('checkID').value = id;
    showMyModalForPhone();
}

function createPhone() {
    if(document.getElementById('checkID').value === 'not') {//создаём новый телефон
        var phone = {};
        phone.id = phones.getLastId();
        phone.countryCode = Number(document.getElementById('countryCode').value);
        phone.operatorCode = Number(document.getElementById('operatorCode').value);
        phone.number = document.getElementById('number').value;
        phone.type = document.getElementById('typeOfPhone').value;
        phone.comment = document.getElementById('comment').value;
        phones.addPhone(phone);
    }
    else{//редактируем существующий по id
        phones.edit(Number(document.getElementById('checkID').value));
    }
    showPhones();
}

function clearPhoneFields() {
    document.getElementById('countryCode').value = '';
    document.getElementById('operatorCode').value = '';
    document.getElementById('number').value = '';
    document.getElementById('typeOfPhone')[0].selected = true;
    document.getElementById('comment').value = '';
    document.getElementById('checkID').value = 'not';
}