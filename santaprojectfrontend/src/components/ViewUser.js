import React, { useState, useEffect } from "react";
import { Card, Image, Header, Button } from "semantic-ui-react";
import { useParams } from "react-router-dom";
import { Link, useNavigate } from "react-router-dom";

export function ViewUser() {
  const navigate = useNavigate();
  const [groups, setGroups] = useState([]);
  const [gifts, setGifts] = useState([]);
  const params = useParams();
  const [user, setUser] = useState({
    name: "",
    email: "",
  });
  const fetchUser = async () => {
    try {
      const response = await fetch("/api/v1/users/" + params.id);
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      const jsonResponse = await response.json();
      setUser(jsonResponse);
      console.log("Fetched User:", jsonResponse);
    } catch (error) {
      console.error("Error fetching user:", error);
    }
  };

  const fetchGroups = async () => {
    try {
      const response = await fetch("/api/v1/groups/user/" + params.id + "/groups"); 
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

  const fetchGifts = async () => {
    try {
      const response = await fetch("/api/v1/gifts/createdBy/" + params.id);
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      const jsonResponse = await response.json();
      setGifts(jsonResponse);
      console.log("Fetched Gifts:", jsonResponse);
    } catch (error) {
      console.error("Error fetching gifts:", error);
    }
  };

  const handleGroupDoubleClick = (groupId) => {
    console.log("groupId:", typeof groupId);
    console.log(`Card clicked for group ID ${groupId}`);
    navigate(`/groups/${groupId}`);
  };

  const handleGiftDoubleClick = (userId, giftId) => {
    console.log(`Card clicked for gift ID ${giftId} of user ID ${userId}`);
    navigate(`/users/${userId}/gifts/${giftId}`);
  };

  const handleCreateGiftClick = (userId) => {
    console.log(`Card clicked for gift ID ${userId}`);
    navigate(`/create/gift/${userId}`);
  };

  useEffect(() => {
    fetchUser();
    fetchGroups();
    fetchGifts();
  }, [params]);

  return (
    <div className="ui centered container">
      <div style={{ display: 'flex' }}>
        <Card style={{ width: '150px', display: 'flex', alignItems: 'center' }} centered>
          <Card.Content>
            <Image src="/images/user.png" circular size='small' centered />
          </Card.Content>
        </Card>
        <Card style={{ width: '300px', display: 'flex', flexDirection: 'column', justifyContent: 'center' }} centered>
          <Card.Content style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', height: '100%' }}>
            <div style={{ flex: '1', textAlign: 'center' }}></div>
            <div style={{ flex: '2', textAlign: 'center' }}>
              <Card.Header style={{ fontSize: '1.5em', marginBottom: '5px' }}>{user.name}</Card.Header>
              <Card.Meta style={{ fontSize: '1.2em' }}>
                <span className="date">{user.email}</span>
              </Card.Meta>
            </div>
          </Card.Content>
        </Card>
      </div>
      <div className="ui two column stackable grid">
        <div className="column">
          <div className="ui" style={{ textAlign: "center" }}>
            <Header as="h2">
              Group List
            </Header>
            <Button
              color="blue"
              className="controls"
              as={Link}
              to="/create/group"
            >
              Create Group
            </Button>
          </div>
          <div className="ui centered cards">
            {groups.map((group) => (
              <Card key={group.id}
                className="m-3 cursor-pointer"
                onDoubleClick={() => handleGroupDoubleClick(group.groupId)}
              >
                
                <Image src="/images/santa.jpg" wrapped ui={false} />
                <Card.Content>
                  <Card.Header>{group.name}</Card.Header>
                  <Card.Meta>
                    <span className="date">Event date is set to {group.eventDate}</span>
                  </Card.Meta>
                  <Card.Description>Event budget is {group.budget}</Card.Description>
                </Card.Content>
              </Card>
            ))}
          </div>
        </div>

        <div className="column">
          <div className="ui" style={{ textAlign: "center" }}>
            <Header as="h2">
              Gift List
            </Header>
            <Button
              color="blue"
              className="controls"
              // as={Link}
              // to="/create/gift"
              onClick={() => handleCreateGiftClick(params.id)}
            >
              Create Gift
            </Button>
          </div>
          <div className="ui centered cards">
            {gifts.map((gift) => (
              <Card
              key={gift.id}
              className="m-3 cursor-pointer"
              onDoubleClick={() => handleGiftDoubleClick(params.id, gift.giftId)}
            >
               
                <Image src="/images/gifts.jpg" wrapped ui={false} />
                <Card.Content>
                  <Card.Header>{gift.name}</Card.Header>
                  <Card.Meta>
                    <span className="date">Belongs in group {gift.group.name}</span>
                  </Card.Meta>
                  <Card.Meta>
                    <span className="date">Description {gift.description}</span>
                  </Card.Meta>
                  Link to the gift: <a href={gift.link} target="_blank" rel="noopener noreferrer">{gift.link}</a>
                  <Card.Description>Gift price {gift.price}</Card.Description>
                </Card.Content>
              </Card>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}