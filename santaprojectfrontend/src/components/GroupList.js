import { useState, useEffect } from "react";
import { Card, Row, Col } from "react-bootstrap";

export function GroupList() {
  const [groups, setGroups] = useState([]);

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
  };

  useEffect(() => {
    fetchGroups();
  }, []);

  return (
    <div className="d-flex justify-content-center">
      {groups.map((group) => (
        <div
          key={group.id}
          className="mb-3 cursor-pointer"
          onDoubleClick={() => handleCardDoubleClick(group.groupId)}
        >
          <Card style={{ width: "18rem" }} key={group.groupId} className="mb-3">
            <Card.Body className="group">
              <Card.Title>{group.name}</Card.Title>
              <Card.Subtitle className="mb-2 text-muted">
                {group.eventDate}
              </Card.Subtitle>
              <Card.Text>Event budget is {group.budget}€</Card.Text>
            </Card.Body>
          </Card>
        </div>
      ))}
    </div>
  );
}
