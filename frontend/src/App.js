import React, { Component } from 'react';
import './App.css';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Home from './components/Home';
import CustomerList from './components/CustomerList';
import CustomerEdit from './components/CustomerEdit';
import CategoryList from './components/CategoryList';
import CategoryEdit from './components/CategoryEdit';
import ProductList from './components/ProductList';
import ProductEdit from './components/ProductEdit';
import OrderList from './components/OrderList';
import OrderEdit from './components/OrderEdit';
import UserList from './components/UserList';
import UserEdit from './components/UserEdit';

class App extends Component {
  render() {
    return (
      <Router>
        <Switch>
          <Route path='/' exact={true} component={Home}/>

          <Route path='/users' exact={true} component={UserList}/>
          <Route path='/users/:id' exact={true} component={UserEdit}/>

          <Route path='/customers' exact={true} component={CustomerList}/>
          <Route path='/customers/:id' exact={true} component={CustomerEdit}/>

          <Route path='/customers/:id/order-carts' exact={true} component={OrderList}/>
          <Route path='/customers/:customerId/order-carts/:id' exact={true} component={OrderEdit}/>

          <Route path='/categories' exact={true} component={CategoryList}/>
          <Route path='/categories/:id' exact={true} component={CategoryEdit}/>

          <Route path='/categories/:id/products' exact={true} component={ProductList}/>
          <Route path='/categories/:categoryId/products/:id' exact={true} component={ProductEdit}/>
        </Switch>
      </Router>
    )
  }
}

export default App;