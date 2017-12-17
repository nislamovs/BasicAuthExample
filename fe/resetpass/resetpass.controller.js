(function () {
    'use strict';

    angular
        .module('app')
        .controller('ResetpassController', ResetpassController);

    ResetpassController.$inject = ['UserService', 'FlashService', '$location'];
    function ResetpassController(UserService, FlashService, $location) {
        var vm = this;

        vm.resetPass = resetPass;

        function resetPass() {
            vm.dataLoading = true;
            UserService.resetpass(vm.user)
                .then(function () {
                        // if (response.success) {
                        FlashService.Success('Your password is being reset. Please check Your mail.', true);
                        $location.path('/login');
                        // } else {
                    }, function(){
                        // FlashService.Error(response.message);
                        FlashService.Error("Password was not reset! Use the old one, dude :)");
                        vm.dataLoading = false;
                    }
                );
        }
    }

})();
