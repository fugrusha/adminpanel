import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

class CategoryList extends Component {

  constructor(props) {
    super(props);
    this.state = {categories: [], isLoading: true};
    this.remove = this.remove.bind(this);
  }

  componentDidMount() {
    this.setState({isLoading: true});

    fetch('/categories')
      .then(response => response.json())
      .then(data => this.setState({categories: data, isLoading: false}));
  }

  async remove(id) {
    await fetch(`/categories/${id}`, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    }).then(() => {
      let updatedCategories = [...this.state.categories].filter(i => i.id !== id);
      this.setState({categories: updatedCategories});
    });
  }

  render() {
    const {categories, isLoading} = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    const categoryList = categories.map(category => {
      return <tr key={category.id}>
        <td style={{whiteSpace: 'nowrap'}}>{category.name}</td>
        <td>
          <ButtonGroup>
            <Button size="sm" color="success" tag={Link} to={"/categories/" + category.id + "/products"}>Open</Button>
            <Button size="sm" color="primary" tag={Link} to={"/categories/" + category.id}>Edit</Button>
            <Button size="sm" color="danger" onClick={() => this.remove(category.id)}>Delete</Button>
          </ButtonGroup>
        </td>
      </tr>
    });

    return (
      <div>
        <AppNavbar/>
        <Container fluid>
          <div className="float-right">
            <Button color="success" tag={Link} to="/categories/new">Add new category</Button>
          </div>
          <h3>Categories</h3>
          <Table className="mt-4">
            <thead>
            <tr>
              <th width="60%">Name</th>
              <th width="40%">Actions</th>
            </tr>
            </thead>
            <tbody>
            {categoryList}
            </tbody>
          </Table>
        </Container>
      </div>
    );
  }
}

export default CategoryList;