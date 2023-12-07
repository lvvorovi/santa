import React, { useState, useEffect } from "react";
import { Button, Image, Card, Icon, Input } from "semantic-ui-react";
import { Link, useNavigate, useParams } from "react-router-dom";

export function GenerateButton({
  onGenerateButtonClick,
  generated,
  recipientName,
}) {
  const params = useParams();

  const [assignedRecipient, setAssignedRecipient] = useState(null);
  const [santaPairs, setSantaPairs] = useState({});
  const [group, setGroup] = useState({
    groupId: "",
    name: "",
    eventDate: "",
    budget: "",
    user: [],
    gifts: [],
    ownerId: "",
    generatedSanta: [],
  });

  const fetchSantaPairs = async () => {
    const groupId = parseInt(params.groupId, 10);
    console.log("groupId:", groupId, typeof groupId); // Log the value and type
    fetch("/api/v1/generate_santa/all_in_group/" + groupId)
      .then((response) => response.json())
      .then(setSantaPairs);
  };

  const generateSanta = async (groupId) => {
    try {
      const response = await fetch(
        `/api/v1/generate_santa/random/${groupId}`, // Use groupId
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
        }
      );

      if (response.ok) {
        const result = await response.json();
        console.log(result);
        return result; // Return the result
      } else {
        console.error("Failed to generate Santa.");
        return null;
      }
    } catch (error) {
      console.error("Error generating Santa:", error);
      return null;
    }
  };

  return (
    <div>
      <Button
        className="generate-button"
        size="large"
        onClick={onGenerateButtonClick}
        color="green"
      >
        {generated
          ? `You are secret Santa to: ${recipientName || "SOMEONE"}`
          : "GENERATE"}
      </Button>
    </div>
  );
}
