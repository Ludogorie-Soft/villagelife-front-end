const handlePrint = () => {
   var actContents = document.body.innerHTML;
   document.body.innerHTML = actContents;
   window.print();
}
   function showConfirmation() {
        if (confirm("Are you sure you want to reject this response?")) {

            return true;
        } else {
            return false;
        }
    }
    function showConfirmationApprove() {
            if (confirm("Are you sure you want to approve this response?")) {

                return true;
            } else {
                return false;
            }
        }