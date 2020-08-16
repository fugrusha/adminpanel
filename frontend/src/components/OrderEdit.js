import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Table, Container, Form, FormGroup } from 'reactstrap';
import AppNavbar from './AppNavbar';

class OrderEdit extends Component {

  emptyItem = {
    orderNumber: '',
    totalSum: '',
    status: '',
    paymentType: '',
    createdDate: '',
    customer: '',
    items: []
  };

  constructor(props) {
    super(props);
    this.state = {
      item: this.emptyItem
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  async componentDidMount() {
    if (this.props.match.params.id !== 'new') {
      const order = await (await fetch(`/customers/${this.props.match.params.customerId}/order-carts/${this.props.match.params.id}`)).json();
      this.setState({item: order});
    }
  }

  handleChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;
    let item = {...this.state.item};
    item[name] = value;
    this.setState({item});
  }

  async handleSubmit(event) {
    event.preventDefault();
    const {item} = this.state;

    let baseUrl = '/customers/' + this.props.match.params.customerId + "/order-carts/"

    await fetch((item.id) ? baseUrl + item.id : baseUrl, {
      method: (item.id) ? 'PATCH' : 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(item),
    });
    this.props.history.push(baseUrl);
  }

  render() {
    const {item} = this.state;
    const title = <h2>{item.id ? 'Edit Order' : 'Add Order'}</h2>;
    let baseUrl = '/customers/' + this.props.match.params.customerId + "/order-carts/"

    const itemList = item.items.map((el, idx) => {
      return ( <tr key={el.id}>
          <td>{idx + 1}</td>
          <td>{el.product.name}</td>
          <td>{el.product.price}</td>
          <td>{el.quantity}</td>
      </tr>
      )
    });

    return <div>
      <AppNavbar/>
      <Container>
        {title}
        <h4>Order # {item.orderNumber}</h4>
        <p>Created date: {item.createdDate}</p>
        
        { 
          <div>
            <h4>Customer</h4>
            <p><b>Name:</b> {item.customer.name} {item.customer.surname}   <b>Phone:</b> {item.customer.phone}</p>
            <p><b>City:</b> {item.customer.city}  <b>Address:</b> {item.customer.address}</p>
          </div>
        }

        <h4>Items:</h4>

        <Table className="mt-4">
            <thead>
            <tr>
              <th>#</th>
              <th width="50%">Product</th>
              <th>Price</th>
              <th>Quantity</th>
            </tr>
            </thead>
            <tbody>
            {itemList}
            </tbody>
          </Table>

          <p>
            <h4>Total sum:</h4> 
            {new Intl.NumberFormat("ua-UA", {
                style: "currency",
                currency: "UAH"
            }).format(item.totalSum)}
          </p>

        <Form onSubmit={this.handleSubmit}>
        <FormGroup>
          <h4>Status</h4> 
          <div>
            <select id="status" name="status" onChange={this.handleChange} value={this.state.item.status}>
              <option value="CANCELED">CANCELED</option>
              <option value="WAITING">WAITING</option>
              <option value="PROCESSED">PROCESSED</option>
              <option value="COMPLETED">COMPLETED</option>
            </select>
          </div>
          </FormGroup>

          <FormGroup>
          <h4>Payment Type</h4> 
          <div>
            <select id="paymentType" name="paymentType" onChange={this.handleChange} value={this.state.item.paymentType}>
              <option value="NP_PAYMENT">NOVA POSHTA PAYMENT</option>
              <option value="PREPAYMENT">PREPAYMENT</option>
            </select>
          </div>
          </FormGroup>
          <FormGroup>
            <Button color="primary" type="submit">Save</Button>{' '}
            <Button color="secondary" tag={Link} to={baseUrl}>Cancel</Button>
          </FormGroup>
        </Form>
      </Container>
    </div>
  }
}

export default withRouter(OrderEdit);