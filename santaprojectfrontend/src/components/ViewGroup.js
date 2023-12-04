import React, { useState, useEffect } from "react";
import { Button, Image, Card, Icon } from "semantic-ui-react";
import { Link, useNavigate, useParams } from "react-router-dom";

export function ViewGroup() {
  const params = useParams();

  const [groupId, setGroupId] = useState("");
  const [group, setGroup] = useState({
    name: "",
    eventDate: "",
    budget: "",
    user: [],
    gifts: [],
    // generatedSanta: [],
  });

  useEffect(() => {
    fetch("/api/v1/groups/" + params.id)
      .then((response) => response.json())
      .then(setGroup);
  }, [params]);

  return (
    <div class="ui one column centered equal width grid">
      <div className="d-flex justify-content-center m-3 centered">
        <div key={group.groupId} className="m-3 cursor-pointer">
          <Card>
            <Image src="/images/santa.jpg" wrapped ui={false} />
            <Card.Content>
              <Card.Header>{group.name}</Card.Header>
              <Card.Meta>
                <span className="date">
                  Event date is set to {group.eventDate}
                </span>
              </Card.Meta>
              <Card.Description>
                Event budget is {group.budget}Є
              </Card.Description>
            </Card.Content>
            <Card.Content extra className="info">
              <a>
                <h3>Participants:</h3>
                <Icon name="user" />
                {group.user.map((user) => (
                  <Button
                    className="button"
                    content="Standard"
                    basic
                    key={user.id}
                  >
                    {user.name},
                  </Button>
                ))}
                {/* Participants list, Add new Participants, Generate button,
                Wishlist */}
                <Button content="Standard" basic className="button">
                  Add new{" "}
                </Button>
              </a>
            </Card.Content>
            <Button className="" inverted color="red" size="large">
              GENERATE
            </Button>
            {/* <Card.Content extra>
              <a>
                <h3>Participants:</h3>
                <Icon name="user" />
                {group.gifts.map((gift) => (
                  <Button
                    className="button"
                    content="Standard"
                    basic
                    key={gift.id}
                  >
                    {gift.name},
                  </Button>
                ))}
                <Button content="Standard" basic className="button">
                  Add new{" "}
                </Button>
              </a>
            </Card.Content> */}
          </Card>
        </div>
      </div>
    </div>
  );
}
