import React, { useState, useEffect } from "react";
import { Card, Image, Header } from "semantic-ui-react";
import { useParams } from "react-router-dom";

export function ViewUser() {
  const params = useParams();
  const [user, setUser] = useState({
    name: "",
    email: "",
  });
  const [groups, setGroups] = useState([]);
  const [ownedGroups, setOwnedGroups] = useState([]);

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
      const response = await fetch("/api/v1/groups/user/"+ params.id +"/groups"); // fix to fetch user groups
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

  const fetchOwnedGroups = async () => {
    try {
      const response = await fetch("/api/v1/groups/owner/"+ params.id +"/groups"); // fix to fetch user owned groups
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      const jsonResponse = await response.json();
      setOwnedGroups(jsonResponse);
      console.log("Fetched Owned Groups:", jsonResponse);
    } catch (error) {
      console.error("Error fetching owned groups:", error);
    }
  };

  useEffect(() => {
    fetchUser();
    fetchGroups();
    fetchOwnedGroups();
  }, [params]);

  return (
    <div className="ui centered container">
      <div className="ui segment">
        <Header as="h2" textAlign="center">User Details</Header>
        <Card centered>
          <Image src="/images/user.png" wrapped ui={false} />
          <Card.Content>
            <Card.Header>{user.name}</Card.Header>
            <Card.Meta>
              <span className="date">Email: {user.email}</span>
            </Card.Meta>
          </Card.Content>
        </Card>
      </div>
      <div className="ui two column stackable grid">
        <div className="column">
          <div className="ui segment" style={{ textAlign: "center" }}>
            <Header as="h2">Groups</Header>
            <div className="ui centered cards">
              {groups.map((group) => (
                <Card key={group.id}>
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
        </div>

        <div className="column">
          <div className="ui segment" style={{ textAlign: "center" }}>
            <Header as="h2">Owned Groups</Header>
            <div className="ui centered cards">
              {ownedGroups.map((group) => (
                <Card key={group.id}>
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
        </div>
      </div>
    </div>
  );
}