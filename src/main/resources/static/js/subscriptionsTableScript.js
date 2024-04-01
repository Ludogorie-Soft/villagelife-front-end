$(document).ready(function() {
    var subscriptionsTable = $('#subscriptionsTable').DataTable({
        dom: 'Bfrtip',
        buttons: [
            {
                extend: 'excelHtml5',
                text: 'Exporting',
                className: 'btn btn-outline-primary col-lg-3 col-md-5 col-sm-12 mb-3',
                exportOptions: {
                    modifier: {
                        selected: null
                    }
                }
            }
        ]
    });

    $('#subscriptionStatus').on('change', function () {
        var status = $(this).val();
        if (status === 'deleted') {
            subscriptionsTable.columns(2).search('^(?!$)', true, false).draw();
        } else if (status === 'notDeleted') {
            subscriptionsTable.columns(2).search('^$', true, false).draw();
        } else {
            subscriptionsTable.columns(2).search('').draw();
        }
    });
});
