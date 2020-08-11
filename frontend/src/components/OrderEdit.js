import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import Moment from "react-moment";
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';

class OrderEdit extends Component {

  emptyItem = {
    totalSum: '',
    status: '',
    createdDate: '',
    customer: '',
    items: ''
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

    return <div>
      <AppNavbar/>
      <Container>
        {title}
        <h4>Order # {item.id}</h4>
        <p>Created date: {item.createdDate}</p>

        <p>Items:</p>

        <Form onSubmit={this.handleSubmit}>
          <FormGroup>
            <Label for="name">Status</Label>
            <Input type="text" name="status" id="status" value={item.status || ''}
                   onChange={this.handleChange} autoComplete="status"/>
          </FormGroup>
          <p>
              Total sum: 
            {new Intl.NumberFormat("ua-UA", {
                style: "currency",
                currency: "UAH"
            }).format(item.totalSum)}
          </p>
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