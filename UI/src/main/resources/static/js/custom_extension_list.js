axios({
    url: 'http://3.39.165.43:5510/api/extension/manage/custom/extensions',
    method: 'get',
})
.then(function (response) {
    console.log(response);
    makeCustomCount(response.data);
    makeCustomList(response.data);
})
.catch(function (error) {
    console.log(error)
});

function makeCustomCount(data) {
    let countEle = document.getElementById('custom-count');
    countEle.innerText = data.length + '/200';
}

function makeCustomList(data) {
    let temp = document.getElementById('custom-temp');
    let previousEle = temp;
    for (const info of data) {
        let clone = temp.cloneNode(false);
        clone.setAttribute('db-id', info.id);
        clone.innerText = info.extensionName;
        previousEle.after(clone);
        previousEle = clone;
    }
    temp.remove();
}