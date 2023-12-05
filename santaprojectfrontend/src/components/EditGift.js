import React, { useState, useEffect } from "react";
import { Button, Image, Card, Form } from "semantic-ui-react";
import { useNavigate, useParams } from "react-router-dom";

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function EditGift() {
  const params = useParams();
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [link, setLink] = useState("");
  const [price, setPrice] = useState("");
  const [group, setGroup] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    fetchGiftDetails(params.giftId);
  }, [params.giftId]);

  const fetchGiftDetails = async (id) => {
    try {
      const response = await fetch(`/api/v1/gifts/${id}`);
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      const giftData = await response.json();
      setName(giftData.name);
      setDescription(giftData.description);
      setLink(giftData.link);
      setPrice(giftData.price);
      setGroup(giftData.group);
    } catch (error) {
      console.error("Error fetching gift details:", error);
    }
  };

  const editGift = () => {
    fetch(`/api/v1/gifts/${params.giftId}`, {
      method: "PUT",
      headers: JSON_HEADERS,
      body: JSON.stringify({
        name,
        description,
        link,
        price,
      }),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.json();
      })
      .then(() => navigate(`/users/${params.userId}`))
      .catch((error) => {
        console.error("Error editing gift:", error);
      });
  };

  return (
    <div className="ui one column centered equal width grid">
      <div className="d-flex justify-content-center m-3 centered">
        <div className="m-3 cursor-pointer">
          <Card>
            <Image src="/images/gifts.jpg" wrapped ui={false} />
            <Card.Content>
              <Card.Header>{name}</Card.Header>
              <Card.Meta>
                <span className="date">
                  Event date is set to {description}
                </span>
              </Card.Meta>
            </Card.Content>
            <Form
              className="m-3 p-3"
              onSubmit={(e) => {
                e.preventDefault();
                editGift();
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

              <Button
                icon
                labelPosition="left"
                onClick={() => navigate(`/users/${params.userId}`)}
              >
                Back
              </Button>
              <Button type="submit">Update</Button>
            </Form>
          </Card>
        </div>
      </div>
    </div>
  );
}
