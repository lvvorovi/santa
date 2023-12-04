import React, { useState, useEffect } from "react";
import { Form, Button, Card, Icon } from "semantic-ui-react";
import { Link, useNavigate } from "react-router-dom";
// import { apiUrl } from "../App";

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function CreateGift() {
  const navigate = useNavigate();
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [link, setLink] = useState("");
  const [price, setPrice] = useState("");
  const [createdBy, setCreatedBy] = useState("");
  const [groupId, setGroupId] = useState("");
  const [users, setUsers] = useState([]);
  const [groupList, setGroupList] = useState([]);

  const createGift = () => {
    fetch("/api/v1/gifts", {
      method: "POST",
      headers: JSON_HEADERS,
      body: JSON.stringify({
        name,
        description,
        link,
        price,
        createdBy,
        groupId,
        //groupDto does not have users
      }),
    })
      //   .then(applyResult)
      .then(() => navigate("/"));
  };

  const fetchGroups = async () => {
    try {
      const response = await fetch("/api/v1/groups");
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      const jsonResponse = await response.json();
      setGroupList(jsonResponse);
      console.log("Fetched Groups:", jsonResponse);
    } catch (error) {
      console.error("Error fetching groups:", error);
    }
  };

  useEffect(() => {
    fetchGroups();
  }, []);

  return (
    <div class="ui one column centered equal width grid">
      <Card className="m-3 p-3">
        <Form
          className="m-3 p-3"
          onSubmit={(e) => {
            e.preventDefault();
            createGift();
          }}
        >
          <Form.Field className="m-3">
            <label>Gift Name</label>
            <input
              placeholder="Gift Name"
              value={name}
              onChange={(e) => setName(e.target.value)}
            />
          </Form.Field>
          <Form.Field>
            <label>Description</label>
            <input
              placeholder="Description"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
            />
          </Form.Field>
          <Form.Field>
            <label>Link</label>
            <input
              placeholder="Link"
              value={link}
              onChange={(e) => setLink(e.target.value)}
            />
          </Form.Field>
          <Form.Field>
            <label>Price</label>
            <input
              placeholder="Price"
              value={price}
              onChange={(e) => setPrice(e.target.value)}
            />
          </Form.Field>
          <Form.Field>
            <label>Created by</label>
            <input
              placeholder="CreatedBy"
              value={createdBy}
              onChange={(e) => setCreatedBy(e.target.value)}
            />
          </Form.Field>
          <Form.Select
            label="Group"
            value={groupId} // Assuming 'group' is the selected group ID
            onChange={(e, { value }) => setGroupId(value)} // Assuming 'setGroup' is the function to set the selected group
            placeholder="Select a Group"
            options={groupList.map((group) => ({
              key: group.groupId,
              text: group.name,
              value: group.groupId,
            }))}
          />


          <Button icon labelPosition="left" className="" as={Link} exact to="/">
            <Icon name="arrow left" />
            Back
          </Button>
          <Button type="submit">Submit</Button>
        </Form>
      </Card>
    </div>
  );
}
