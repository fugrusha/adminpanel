import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

class OrderList extends Component {

  constructor(props) {
    super(props);
    this.state = {orders: [], isLoading: true};
    this.remove = this.remove.bind(this);
  }

  componentDidMount() {
    this.setState({isLoading: true});

    fetch(`/customers/${this.props.match.params.id}/order-carts`)
      .then(response => response.json())
      .then(data => this.setState({orders: data, isLoading: false}));
  }

  async remove(customerId, id) {
    await fetch(`/customers/${customerId}/order-carts/${id}`, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    }).then(() => {
      let updatedOrders = [...this.state.orders].filter(i => i.id !== id);
      this.setState({orders: updatedOrders});
    });
  }

  render() {
    const {orders, isLoading} = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    const orderList = orders.map(order => {
      return <tr key={order.id}>
        <td style={{whiteSpace: 'nowrap'}}>{order.createdDate}</td>
        <td>{order.status}</td>
        <td>{order.totalSum}</td>
        <td>
          <ButtonGroup>
            <Button size="sm" color="primary" tag={Link} to={"/customers/" + order.customerId + "/order-carts/" + order.id}>Edit</Button>
            <Button size="sm" color="danger" onClick={() => this.remove(order.customerId, order.id)}>Delete</Button>
          </ButtonGroup>
        </td>
      </tr>
    });

    return (
      <div>
        <AppNavbar/>
        <Container fluid>
          <h3>Orders</h3>
          <Table className="mt-4">
            <thead>
            <tr>
              <th width="10%">Date</th>
              <th>Status</th>
              <th width="20%">Total Sum</th>
              <th width="10%">Actions</th>
            </tr>
            </thead>
            <tbody>
            {orderList}
            </tbody>
          </Table>
        </Container>
      </div>
    );
  }
}

export default OrderList;