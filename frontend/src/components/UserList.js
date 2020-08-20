import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

const API_URL = '/api/v1';

class UserList extends Component {

  constructor(props) {
    super(props);
    this.state = {users: [], isLoading: true};
    this.remove = this.remove.bind(this);
  }

  componentDidMount() {
    this.setState({isLoading: true});

    fetch(API_URL + `/users`)
      .then(response => response.json())
      .then(data => this.setState({users: data, isLoading: false}));
  }

  async remove(id) {
    await fetch(API_URL + `/users/${id}`, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    }).then(() => {
      let updatedUsers = [...this.state.users].filter(i => i.id !== id);
      this.setState({users: updatedUsers});
    });
  }

  render() {
    const {users, isLoading} = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    const userList = users.map(user => {
      return <tr key={user.id}>
        <td>{user.chatId}</td>
        <td style={{whiteSpace: 'nowrap'}}>{user.username}</td>
        <td>{user.isBlocked ? <span>Yes</span> : <span>No</span>}</td>
        <td>
          <ButtonGroup>
            <Button size="sm" color="primary" tag={Link} to={"/users/" + user.id}>Edit</Button>
            <Button size="sm" color="danger" onClick={() => this.remove(user.id)}>Delete</Button>
          </ButtonGroup>
        </td>
      </tr>
    });

    return (
      <div>
        <AppNavbar/>
        <Container fluid>
          <div className="float-right">
            <Button color="success" tag={Link} to={`/users/new`}>Add new user</Button>
          </div>
          <h3>Users</h3>
          <Table className="mt-4">
            <thead>
            <tr>
              <th width="10%">Chat ID</th>
              <th width="20%">Username</th>
              <th>Is Blocked</th>
              <th width="10%">Actions</th>
            </tr>
            </thead>
            <tbody>
            {userList}
            </tbody>
          </Table>
        </Container>
      </div>
    );
  }
}

export default UserList;