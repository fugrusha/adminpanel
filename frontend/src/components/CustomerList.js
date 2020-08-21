import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

const API_URL = '/api/v1';

class CustomerList extends Component {

  constructor(props) {
    super(props);
    this.state = {customers: [], isLoading: true};
  }

  componentDidMount() {
    this.setState({isLoading: true});

    fetch(API_URL + '/customers')
      .then(response => response.json())
      .then(data => this.setState({customers: data, isLoading: false}));
  }

  render() {
    const {customers, isLoading} = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    const customerList = customers.map(customer => {
      const address = `${customer.address || ''} ${customer.city || ''}`;
      const fullName = `${customer.name || ''} ${customer.surname || ''}`;
      return <tr key={customer.id}>
        <td style={{whiteSpace: 'nowrap'}}>{fullName}</td>
        <td>{customer.phone}</td>
        <td>{address}</td>
        <td>{customer.username}</td>
        <td>{customer.chatId}</td>
        <td>
          <ButtonGroup>
            <Button size="sm" color="success" tag={Link} to={"/customers/" + customer.id + "/order-carts"}>Open</Button>
            <Button size="sm" color="primary" tag={Link} to={"/customers/" + customer.id}>Edit</Button>
          </ButtonGroup>
        </td>
      </tr>
    });

    return (
      <div>
        <AppNavbar/>
        <Container fluid>
          <h3>Customers</h3>
          <Table className="mt-4">
            <thead>
            <tr>
              <th width="10%">Full Name</th>
              <th>Phone</th>
              <th>Address</th>
              <th>Username</th>
              <th>ChatId</th>
              <th width="10%">Actions</th>
            </tr>
            </thead>
            <tbody>
            {customerList}
            </tbody>
          </Table>
        </Container>
      </div>
    );
  }
}

export default CustomerList;