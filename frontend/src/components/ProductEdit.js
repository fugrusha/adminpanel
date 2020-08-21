import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';

const API_URL = '/api/v1';

class ProductEdit extends Component {

  emptyItem = {
    orderNumber: '',
    name: '',
    description: '',
    photoUrl: '',
    price: ''
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
      const product = await (await fetch(API_URL + `/categories/${this.props.match.params.categoryId}/products/${this.props.match.params.id}`)).json();
      this.setState({item: product});
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

    let baseUrl = '/categories/' + this.props.match.params.categoryId + "/products/"

    await fetch((item.id) ? API_URL + baseUrl + item.id : API_URL + baseUrl, {
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
    const title = <h2>{item.id ? 'Edit Product' : 'Add Product'}</h2>;
    let baseUrl = '/categories/' + this.props.match.params.categoryId + "/products/"

    return <div>
      <AppNavbar/>
      <Container>
        {title}
        <Form onSubmit={this.handleSubmit}>
          <FormGroup>
            <Label for="name">Name</Label>
            <Input type="text" name="name" id="name" value={item.name || ''}
                   onChange={this.handleChange} autoComplete="name"/>
          </FormGroup>
          <FormGroup>
            <Label for="description">Description</Label>
            <Input type="text" name="description" id="description" value={item.description || ''}
                   onChange={this.handleChange} autoComplete="description"/>
          </FormGroup>
          <FormGroup>
            <Label for="photoUrl">Telegram Photo ID</Label>
            <Input type="text" name="photoUrl" id="photoUrl" value={item.photoUrl || ''}
                   onChange={this.handleChange} autoComplete="photoUrl"/>
          </FormGroup>
          <FormGroup>
            <Label for="orderNumber">Order Number</Label>
            <Input type="text" name="orderNumber" id="orderNumber" value={item.orderNumber || ''}
                   onChange={this.handleChange} autoComplete="orderNumber"/>
          </FormGroup>
          <FormGroup>
            <Label for="price">Price</Label>
            <Input type="text" name="price" id="price" value={item.price || ''}
                   onChange={this.handleChange} autoComplete="price"/>
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

export default withRouter(ProductEdit);