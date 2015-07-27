angular.module('dictionaryApp', ['ngRoute'])
    .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/', {
                    templateUrl: '/webshop/productsView.html',
                    controller: 'ShopController as shopController'
                });

            }])
    .controller('ShopController', ['$http', function ($http) {
                    var self = this;
                    self.message = "";
                    self.products = [];
                    self.orders = [];
                    self.order = {items: []};
                    self.buy = function () {
                        self.message = "";
                        var isValid = true;
                        angular.forEach(self.products, function (value, key) {
                            if (value.product.quantity > value.availableQuantity) {
                                self.message = "Invalid quantity";
                                self.order = {items: []};
                                isValid = false;
                                return false;
                            } else if (value.product.quantity > 0) {
                                var item = {
                                    productId: value.product.id,
                                    quantity: value.product.quantity
                                };
                                self.order.items.push(item);
                            }
                        });
                        if (isValid) {
                            $http.post('rs-api/shop/v1.0/order', JSON.stringify(self.order))
                                .then(function (response) {
                                          self.orders.push(response.data);
                                          getProducts();
                                          self.order = {items: []};
                                      }, function (errResponse) {
                                          self.message = errResponse.data.message;
                                      });
                        }

                    };

                    var getProducts = function () {
                        console.log('getting products')
                        return $http.get('/webshop/rs-api/shop/v1.0/products').then(function (response) {
                            self.products = response.data;
                        }, function (errResponse) {
                            console.error('An error occured: ' + errResponse);
                        });
                    };
                    getProducts();
                }]);