(function () {
    'use strict';

    angular
        .module('app')
        .factory('UserService', UserService);

    UserService.$inject = ['$http', '$q'];
    function UserService($http, $q) {

        var prefix="http://localhost:8080";
        var service = {};

        service.GetAll = GetAll;
        service.GetById = GetById;
        service.GetByUsername = GetByUsername;
        service.Create = Create;
        service.resetpass = resetpass;
        service.Update = Update;
        service.Delete = Delete;

        return service;

        function GetAll() {
            return $http.get(prefix+'/api/account/').then(handleSuccess, handleError('Error getting all users'));
        }

        function GetById(id) {
            return $http.get(prefix+'/api/account/' + id).then(handleSuccess, handleError('Error getting user by id'));
        }

        function GetByUsername(username) {
            return $http.get(prefix+'/api/account/' + username).then(handleSuccess, handleError('Error getting user by username'));
        }

        function Create(user) {

            var deferred = $q.defer();
            var newUser = {};

            newUser.name=user.firstName;
            newUser.lastName=user.lastName;
            newUser.email=user.username;
            newUser.password=user.password;

            $http.post(prefix+'/register/', newUser)
                .then(function(response) {
                    deferred.resolve(response.data.msg);
                }, function(response) {
                    deferred.reject(response.data.msg);
                });

            return deferred.promise;
        }

        function resetpass(user) {

            var deferred = $q.defer();
            var username = {};

            username.email=user.username;

            $http.put(prefix+'/resetpass/', username)
                .then(function() {
                    deferred.resolve("Login reset ok!");
                }, function() {
                    deferred.reject("Login reset not ok!");
                });

            return deferred.promise;
        }

        function Update(user) {
            return $http.put(prefix+'/api/account/' + user.id, user).then(handleSuccess, handleError('Error updating user'));
        }

        function Delete(id) {
            return $http.delete(prefix+'/api/account/' + id).then(handleSuccess, handleError('Error deleting user'));
        }

        // private functions

        function handleSuccess(res) {
            return res.data;
        }

        function handleError(error) {
            return function () {
                return { success: false, message: error };
            };
        }
    }

})();
