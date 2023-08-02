const mealsAjaxUrl = "admin/meals/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mealsAjaxUrl
};

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "asc"
                ]
            ]
        })
    );
});

var filter="";

function getFilter(){
    $.ajax({
        url: ctx.ajaxUrl + "filter/?" + $('#filterForm').serialize(),
        type: "GET"
    }).done(function (data){
        clearAndUpdateTable(data);
        filter = "filter/?" + $('#filterForm').serialize();
    });
}

function resetFilter(){
    filter="";
    updateTable();
}

function updateTable() {
    $.get(ctx.ajaxUrl + filter, function (data) {
        clearAndUpdateTable(data);
    });
}