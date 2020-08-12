import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';

class UserEdit extends Component {

  emptyItem = {
    username: '',
    chatId: '',
    idBlocked: true
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
      const user = await (await fetch(`/users/${this.props.match.params.id}`)).json();
      this.setState({item: user});
    }
  }

  handleChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;
    let item = {...this.state.item};
    if (name === "isBlocked") {
        var checked = event.target.checked;
        item[name] = checked;
    } else {
        item[name] = value;
    }
    
    this.setState({item});
  }

  async handleSubmit(event) {
    event.preventDefault();
    const {item} = this.state;

    await fetch(item.id ? '/users/' + item.id : '/users', {
      method: (item.id) ? 'PATCH' : 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(item),
    });
    this.props.history.push('/users');
  }

  render() {
    const {item} = this.state;
    const title = <h2>{item.id ? 'Edit User' : 'Add User'}</h2>;

    return <div>
      <AppNavbar/>
      <Container>
        {title}
        <Form onSubmit={this.handleSubmit}>
          <FormGroup>
            <Label for="username">Username</Label>
            <Input type="text" name="username" id="username" value={item.username || ''}
                   onChange={this.handleChange} autoComplete="username"/>
          </FormGroup>
          <FormGroup>
            <Label for="chatId">Chat ID</Label>
            <Input type="text" name="chatId" id="chatId" value={item.chatId || ''}
                   onChange={this.handleChange} autoComplete="chatId"/>
          </FormGroup>
          <FormGroup>
            <Input type="checkbox" name="isBlocked" id="isBlocked" defaultChecked={item.isBlocked}
                   onChange={this.handleChange} autoComplete="isBlocked"/>
            <Label for="isBlocked">Is Blocked</Label>
          </FormGroup>
          <FormGroup>
            <Button color="primary" type="submit">Save</Button>{' '}
            <Button color="secondary" tag={Link} to="/users">Cancel</Button>
          </FormGroup>
        </Form>
      </Container>
    </div>
  }
}

export default withRouter(UserEdit);