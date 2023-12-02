import React, { useState } from "react";
import { Form, Button, Card, Icon } from "semantic-ui-react";
import { Link, useNavigate } from "react-router-dom";

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

const options = [
  { key: "t", text: "Tom", value: "Tom" },
  { key: "m", text: "Mary", value: "Mary" },
  { key: "l", text: "Lisa", value: "Lisa" },
  { key: "r", text: "Rose", value: "Rose" },
  { key: "1", text: "Andrew", value: "Andrew" },
];

export function CreateGroup() {
  const navigate = useNavigate();
  const [name, setName] = useState("");
  const [eventDate, setEventDate] = useState("");
  const [budget, setBudget] = useState("");

  const createGroup = () => {
    fetch("/api/v1/groups", {
      method: "POST",
      headers: JSON_HEADERS,
      body: JSON.stringify({
        name,
        eventDate,
        budget,
      }),
    })
      //   .then(applyResult)
      .then(() => navigate("/"));
  };

  return (
    <div class="ui one column centered equal width grid">
      <Card className="m-3 p-3">
        <Form
          className="m-3 p-3"
          onSubmit={(e) => {
            e.preventDefault();
            createGroup();
          }}
        >
          <Form.Field className="m-3">
            <label>Event Name</label>
            <input
              placeholder="Event Name"
              value={name}
              onChange={(e) => setName(e.target.value)}
            />
          </Form.Field>
          <Form.Field>
            <label>Event date</label>
            <input
              placeholder="Event date"
              value={eventDate}
              onChange={(e) => setEventDate(e.target.value)}
            />
          </Form.Field>
          <Form.Field>
            <label>Agreed budget</label>
            <input
              placeholder="Agreed budget"
              value={budget}
              onChange={(e) => setBudget(e.target.value)}
            />
          </Form.Field>
          <Form.Select
            label="Participants"
            options={options}
            placeholder="Participants"
          ></Form.Select>
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
