import React from "react";
import { Card, Feed } from "semantic-ui-react";

export function GiftList() {
  return (
    <div>
      <Card style={{ width: "1300px" }}>
        <Card.Content>
          <Card.Header>Gift Wishlist</Card.Header>
        </Card.Content>
        <Card.Content>
        <Card.Group style={{ display: "flex", justifyContent: "center" }}>
            <Card
              image="/images/gifts.jpg"
              header="NICEST GIFT"
              style={{ width: "150px", height: "150px" }}
              // extra={extra}
            />
            <Card
              image="/images/gifts.jpg"
              header="NICEST GIFT"
              style={{ width: "150px", height: "150px" }}
              // extra={extra}
            />
            <Card
              image="/images/gifts.jpg"
              header="NICEST GIFT"
              style={{ width: "150px", height: "150px" }}
              // extra={extra}
            />
            <Card
              image="/images/gifts.jpg"
              header="NICEST GIFT"
              style={{ width: "150px", height: "150px" }}
              //extra={extra}
            />
          </Card.Group>
        </Card.Content>
      </Card>
    </div>
  );
}
