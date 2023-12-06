import { useState, useEffect } from "react";
import { Card, Icon, Image, Button } from "semantic-ui-react";
import { Link, useNavigate } from "react-router-dom";
import "./SecretSanta.css";

export function GroupList() {
  const navigate = useNavigate();
  const [groups, setGroups] = useState([]);
  const [imageUrls, setImageUrls] = useState([
    "/images/santa1.jpg",
    "/images/santa2.jpg",
    "/images/santa3.jpg",
    "/images/santa4.jpg",
    "/images/santa5.jpg",
    "/images/santa6.jpg",
    "/images/santa7.jpg",
  ]);

  const fetchGroups = async () => {
    try {
      const response = await fetch("/api/v1/groups");
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      const jsonResponse = await response.json();
      setGroups(jsonResponse);
      console.log("Fetched Groups:", jsonResponse);
    } catch (error) {
      console.error("Error fetching groups:", error);
    }
  };

  const handleCardDoubleClick = (groupId) => {
    console.log(`Card clicked for group ID ${groupId}`);
    navigate(`/groups/${groupId}`);
  };

  const getRandomImageUrl = () => {
    const randomIndex = Math.floor(Math.random() * imageUrls.length);
    return imageUrls[randomIndex];
  };

  useEffect(() => {
    fetchGroups();
  }, []);

  return (
    <div class="ui one column centered equal width grid">
      <div className="d-flex justify-content-center m-3 centered backgroundImage">
        {groups.map((group) => (
          <div
            key={group.id}
            className="m-3"
            onDoubleClick={() => handleCardDoubleClick(group.groupId)}
          >
            <Card.Group>
              <Card centered m-3 className="card">
                <Image src={getRandomImageUrl()} wrapped ui={false} />
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
              </Card>
            </Card.Group>
          </div>
        ))}
        <div className="m-3">
          <Button
            basic
            color="red"
            icon
            className="controls"
            as={Link}
            to="/create/group"
          >
            Create new event
          </Button>
        </div>
        {/* <Card.Group>
          {groups.map((group) => (
            <div
              key={group.id}
              className="mb-3 cursor-pointer"
              onDoubleClick={() => handleCardDoubleClick(group.groupId)}
            >
              <Card>
                <Card.Content>
                  <Card.Header>{group.name}</Card.Header>
                  <Card.Meta>{group.eventDate}</Card.Meta>
                  <Card.Description>
                    Event budget is {group.budget}
                  </Card.Description>
                </Card.Content>
              </Card>
            </div>
          ))}
        </Card.Group> */}
      </div>
    </div>
  );
}
