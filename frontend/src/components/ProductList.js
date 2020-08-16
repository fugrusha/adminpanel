import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

class ProductList extends Component {

  constructor(props) {
    super(props);
    this.state = {products: [], isLoading: true};
    this.remove = this.remove.bind(this);
  }

  componentDidMount() {
    this.setState({isLoading: true});

    fetch(`/categories/${this.props.match.params.id}/products`)
      .then(response => response.json())
      .then(data => this.setState({products: data, isLoading: false}));
  }

  async remove(categoryId, id) {
    await fetch(`/categories/${categoryId}/products/${id}`, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    }).then(() => {
      let updatedProducts = [...this.state.products].filter(i => i.id !== id);
      this.setState({products: updatedProducts});
    });
  }

  render() {
    const {products, isLoading} = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    const productList = products.map(product => {
      return <tr key={product.id}>
        <td>{product.orderNumber}</td>
        <td style={{whiteSpace: 'nowrap'}}>{product.name}</td>
        <td>{product.description}</td>
        <td>{product.price}</td>
        <td>
          <ButtonGroup>
            <Button size="sm" color="primary" tag={Link} to={"/categories/" + product.categoryId + "/products/" + product.id}>Edit</Button>
            <Button size="sm" color="danger" onClick={() => this.remove(product.categoryId, product.id)}>Delete</Button>
          </ButtonGroup>
        </td>
      </tr>
    });

    return (
      <div>
        <AppNavbar/>
        <Container fluid>
          <div className="float-right">
            <Button color="success" tag={Link} to={`/categories/${this.props.match.params.id}/products/new`}>Add new product</Button>
          </div>
          <h3>Products</h3>
          <Table className="mt-4">
            <thead>
            <tr>
              <th>#</th>
              <th width="30%">Name</th>
              <th width="30%">Description</th>
              <th width="10%">Price</th>
              <th width="10%">Actions</th>
            </tr>
            </thead>
            <tbody>
            {productList}
            </tbody>
          </Table>
        </Container>
      </div>
    );
  }
}

export default ProductList;