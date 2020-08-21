import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table, Form, FormGroup, Label} from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

const API_URL = '/api/v1';

class OrderFilterList extends Component {

    emptyFilter = {
        status: ''
    }

  constructor(props) {
    super(props);
    this.state = {
        orders: [],
        filter: this.emptyFilter,
        isLoading: true};

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  componentDidMount() {
    this.setState({isLoading: true});

    fetch(API_URL + `/order-carts`)
      .then(response => response.json())
      .then(data => this.setState({orders: data, isLoading: false}));
  }

  handleChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;
    let filter = {...this.state.filter};
    filter[name] = value;
    this.setState({filter}); 
  }

  async handleSubmit(event) {
    this.setState({isLoading: true});

    event.preventDefault();
    const {filter} = this.state;

    if(filter.status === 'ALL') {
        fetch(API_URL + `/order-carts`)
            .then(response => response.json())
            .then(data => this.setState({orders: data, isLoading: false}));
    } else {
        await fetch(API_URL + '/order-carts?status=' + filter.status, {
            method: 'GET',
            headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json'
            },
          }).then(response => response.json())
            .then(data => this.setState({orders: data, isLoading: false}));
    }
  }

  render() {
    const {orders, isLoading} = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    const orderList = orders.map(order => {
      return <tr key={order.id}>
        <td style={{whiteSpace: 'nowrap'}}>{order.orderNumber}</td>
        <td style={{whiteSpace: 'nowrap'}}>{order.createdDate}</td>
        <td>{order.status}</td>
        <td>{order.paymentType}</td>
        <td>{order.totalSum}</td>
        <td>
          <ButtonGroup>
            <Button size="sm" color="primary" tag={Link} to={"/customers/" + order.customerId + "/order-carts/" + order.id}>Edit</Button>
          </ButtonGroup>
        </td>
      </tr>
    });

    return (
      <div>
        <AppNavbar/>
        <Container fluid>
          <h3>Orders</h3>

          <Form onSubmit={this.handleSubmit}>
            <FormGroup>
            <Label for="status">Search by status</Label>
            <div>
                <select id="status" name="status" onChange={this.handleChange} value={this.state.filter.status}>
                    <option value="ALL">ALL</option>
                    <option value="CANCELED">CANCELED</option>
                    <option value="WAITING">WAITING</option>
                    <option value="PROCESSED">PROCESSED</option>
                    <option value="COMPLETED">COMPLETED</option>
                </select>
            </div>
            </FormGroup>
            <FormGroup>
                <Button color="primary" type="submit">Load</Button>{' '}
            </FormGroup>
          </Form>

          <Table className="mt-4">
            <thead>
            <tr>
              <th width="10%">Order Number</th>
              <th width="10%">Date</th>
              <th>Status</th>
              <th>Payment Type</th>
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

export default OrderFilterList;