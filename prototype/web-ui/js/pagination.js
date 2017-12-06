$(function () {
            $("#user_table").hpaging({ "limit": 10 });
        });

        $("#btnApply").click(function () {
            var lmt = $("#pglmt").val();
            $("#user_table").hpaging("newLimit", lmt);
        });

