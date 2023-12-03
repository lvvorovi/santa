import React, { useState, useEffect } from "react";
import { Form, Image, Card } from "semantic-ui-react";
import { useParams } from "react-router-dom";

export function ViewUser() {
  const params = useParams();
  const [user, setUser] = useState({
    name: "",
    email: "",
    ownedGroups: [],
    groups: [],
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

  useEffect(() => {
    fetchUser();
  }, [params]);

  return (
   
    <div className="ui centered grid">
    <div className="row">
      <div className="sixteen wide column">
        <h2 className="ui center aligned header">User Details</h2>
        <div className="ui centered cards">
          {/* Display user details */}
          <Card>
            <Image src="/images/user.png" wrapped ui={false} />
            <Card.Content>
              <Card.Header>{user.name}</Card.Header>
              <Card.Meta>
                <span className="date">Email: {user.email}</span>
              </Card.Meta>
            </Card.Content>
          </Card>
        </div>
      </div>
    </div>

      <div className="row">
        <div className="eight wide centered column" style={{ textAlign: "center" }}>
          <h2>Groups</h2>
          <div className="d-flex justify-content-center m-3 centered">
           {/* Display groups */}
           {user.groups?.map((group) => (
              <Card key={group.id}>
                <Card.Content>
                  <Card.Header>{group.name}</Card.Header>
                  <Card.Meta>
                    <span className="date">
                      Event date is set to {group.eventDate}
                    </span>
                  </Card.Meta>
                  <Card.Description>
                    Event budget is {group.budget}
                  </Card.Description>
                </Card.Content>
              </Card>
            ))}
          </div>
        </div>

        <div className="eight wide centered column" style={{ textAlign: "center" }}>
          <h2>Owned Groups</h2>
          <div className="d-flex justify-content-center m-3 centered">
            {/* Display owned groups */}
            {user.ownedGroups?.map((group) => (
              <Card key={group.id}>
                <Card.Content>
                  <Card.Header>{group.name}</Card.Header>
                  <Card.Meta>
                    <span className="date">
                      Event date is set to {group.eventDate}
                    </span>
                  </Card.Meta>
                  <Card.Description>
                    Event budget is {group.budget}
                  </Card.Description>
                </Card.Content>
              </Card>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}
