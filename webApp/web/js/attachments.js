var attachments = new Attachments();
var editing = false;
function showAttachments() {
    var pattern = '{{#attachments}}<tr><th scope="row"><input type="checkbox" class="form-check-input" name="checkAttachment" id="{{id}}"></th>' +
        '<td><a href="#" onclick="fillAttachment({{id}})">{{name}}</a></td><td>{{date}}</td><td>{{comment}}</td>' +
        '<td><a href="#" style="visibility: {{vis}}" onclick="downloadAttachment({{id}})">Download</a></td></tr>{{/attachments}}';
    document.getElementById('attachmentsBody').innerHTML = Mustache.render(pattern, attachments);
}

function fillAttachment(id) {
    var tempAttach = attachments.getAttachmentByID(id);
    document.getElementById('commentOfAttach').value = tempAttach.comment;
    document.getElementById('checkAttachID').value = id;
    editing = true;
    showMyModalForAttachment();
}

function clearAttachFields() {
    editing = false;
    document.getElementById('commentOfAttach').value = '';
    document.getElementById('checkAttachID').value = 'not';
}

function createAttachment() {
    if(document.getElementById('checkAttachID').value === 'not') {//создаём новый attachment
        var attachment = {};
        var file = document.getElementById('attach').files[0];
        if(file){
            attachment.id = attachments.getLastId();
            attachment.comment = document.getElementById('commentOfAttach').value;
            attachment.date = null;
            attachment.name = document.getElementById('attach').files[0].name;
            attachment.vis = 'hidden';
            attachments.addAttachment(attachment);

            var type = getType(document.getElementById('attach').files[0].name);
            attachToForm(attachment.id);
        }
    }
    else{//редактируем существующий по id
        attachments.edit(Number(document.getElementById('checkAttachID').value));
    }
    showAttachments();
}

function Attachments(){
    this.attachments = [];
    this.lastId = 1;

    this.getAttachments = function () {
        var resultAttachments = [];
        for(var i = 0; i < this.attachments.length; i++){
            var attach = {};
            attach.id = this.attachments[i].id;
            attach.comment = this.attachments[i].comment;
            attach.date = this.attachments[i].date;
            attach.name = this.attachments[i].name;
            resultAttachments[resultAttachments.length] = attach;
        }
        return resultAttachments;
    };

    this.addAttachment = function (attachment) {
        this.attachments[this.attachments.length] = attachment;
    };

    this.myDelete = function (ids) {
        for(var i = 0; i < ids.length; i++){
            for(var j = 0; j < this.attachments.length; j++){
                if(this.attachments[j].id === ids[i]){
                    this.attachments.splice(j, 1);
                    if(ids[i] <= 0) {
                        removeFromForm(ids[i]);
                    }
                }
            }
        }
    };

    this.getLastId = function () {
        this.lastId -= 1;
        return this.lastId;
    };

    this.getAttachmentByID = function (id) {
        for(var i = 0; i < this.attachments.length; i++){
            if(this.attachments[i].id === id){
                return this.attachments[i];
            }
        }
    };

    this.edit = function (id) {
        var temp = attachments.getAttachmentByID(id);
        temp.comment = document.getElementById('commentOfAttach').value;
        var file = document.getElementById('attach').files[0];
        if(file){
            temp.date = null;
            temp.name = document.getElementById('attach').files[0].name;
            temp.vis = 'hidden';

            var type = getType(document.getElementById('attach').files[0].name);
            document.getElementById('attach').files[0].name = id + type;

            removeFromForm(temp.id);
            attachToForm(temp.id);
        }
    }
}

function getType(name) {
    var mas = name.split('.');
    var type = name[1];
    return '.' + type;
}

function deleteAttachments() {
    var ids = getSelectedItems('checkAttachment');
    attachments.myDelete(ids);
    hideMyDangerAttachmentModal();
    showAttachments();
}

function downloadAttachment(id) {
    document.getElementById('attachmentId').value = id;
    document.getElementById('submitDownload').click();
}

function attachToForm(id) {
    //поместим старый input в форму контакта
    var old = document.getElementById("attach");//берём input с файлом из модалки
    old.id = id;//меняем его id
    var copy = old.cloneNode();//копируем его
    copy.style.display = "none";//делаем input невидимым
    copy.name = id;
    document.getElementById("formOfContact").appendChild(copy);//добавляем его на форму

    //создадим новый input для modal'ки
    var newInput = document.createElement("input");
    newInput.setAttribute("class", "form-control");
    newInput.setAttribute("type", "file");
    newInput.required = true;
    newInput.setAttribute("id", "attach");//создали input
    var form = document.getElementById('addAttachmentForm');//берём форму из модалки
    form.childNodes[1].childNodes[3].replaceChild(newInput, form.childNodes[1].childNodes[3].childNodes[3]);
}

function removeFromForm(id) {
    var nested = document.getElementsByName(id)[0];
    if (nested.parentNode) {
        nested.parentNode.removeChild(nested);
    }
}