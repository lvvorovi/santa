import React, { useState, useEffect } from "react";
import { Button, Image, Card, Icon, Input } from "semantic-ui-react";
import { Link, useNavigate, useParams } from "react-router-dom";
import { GenerateButton } from "./GenerateButton";
import { GiftList } from "./GiftList";
import { WishList } from "./WishList";

export function ViewGroup() {
  const params = useParams();

  const [addingUser, setAddingUser] = useState(false);
  const [newUserName, setNewUserName] = useState("");
  const [filteredUsers, setFilteredUsers] = useState([]);
  const [generated, setGenerated] = useState(false);
  const [assignedRecipient, setAssignedRecipient] = useState("");
  const [users, setUsers] = useState([]);
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

  const fetchGroups = async () => {
    const groupId = parseInt(params.groupId, 10);
    console.log("groupId:", groupId, typeof groupId); // Log the value and type
    fetch("/api/v1/groups/" + groupId)
      .then((response) => response.json())
      .then(setGroup);
  };

  const fetchUsers = async () => {
    fetch("/api/v1/users")
      .then((response) => response.json())
      .then(setUsers);
  };

  const handleAddNewUser = () => {
    console.log(users);
    setAddingUser(true);
  };

  const handleNewUserInputChange = (e) => {
    setNewUserName(e.target.value);
  };

  const fetchFilteredUsers = async () => {
    if (newUserName.trim() !== "") {
      try {
        const response = await fetch(
          `/api/v1/users/search?name=${newUserName}`
        );
        if (response.ok) {
          const matchingUsers = await response.json();
          console.log("matchingUsers", matchingUsers);
          setFilteredUsers(matchingUsers);
        } else {
          console.error("Failed to fetch filtered users.");
        }
      } catch (error) {
        console.error("Error fetching filtered users:", error);
      }
    } else {
      setFilteredUsers([]);
    }
  };

  const handleAddUser = async (selectedUser) => {
    setNewUserName(selectedUser.name);
    setFilteredUsers([]);

    try {
      const response = await fetch(
        `/api/v1/users/search?name=${selectedUser.name}`
      );

      if (response.ok) {
        const users = await response.json();
        const user = Array.isArray(users) && users.length > 0 ? users[0] : null;
        console.log("user", user);
        if (user) {
          const addResponse = await fetch(
            `/api/v1/groups/${parseInt(params.groupId)}/users/${parseInt(
              user.userId
            )}/newUsers`,
            {
              method: "POST",
              headers: {
                "Content-Type": "application/json",
              },
              body: JSON.stringify(user),
            }
          );

          if (addResponse.ok) {
            const updatedGroup = await addResponse.json();
            setGroup(updatedGroup);
            setAddingUser(false);
            setNewUserName("");
          } else {
            console.error("Failed to add user to the group.");
          }
        } else {
          console.error("User not found.");
        }
      } else {
        console.error("Failed to fetch user details.");
      }
    } catch (error) {
      console.error("Error adding user:", error);
    }
  };

  const checkSantaPairs = async () => {
    const userId = parseInt(params.userId);
    const groupId = parseInt(params.groupId);
    console.log("!!!userId", userId);

    try {
      const response = await fetch(
        `/api/v1/generate_santa/santa_group/${userId}?groupId=${groupId}`
      );

      if (response.ok) {
        const santaPairs = await response.json();
        console.log("santapair", santaPairs);

        // Check if santaPairs is an object and has 'santa' and 'recipient' properties
        if (
          santaPairs &&
          typeof santaPairs === "object" &&
          santaPairs.hasOwnProperty("santa") &&
          santaPairs.hasOwnProperty("recipient")
        ) {
          setAssignedRecipient(santaPairs.recipient);
          setGenerated(true);
        } else {
          console.error("Invalid format of Santa pairs data.");
        }
      } else {
        console.error("Failed to fetch Santa pairs.");
      }
    } catch (error) {
      console.error("Error checking Santa pairs:", error);
    }
  };



  // const handleGenerateButtonClick = async () => {
  //   const groupId = parseInt(params.groupId, 10);
  //   console.log("Group ID:", groupId);
  //   console.log("User ID:", parseInt(params.userId));
  //   const response = await generateSanta(groupId);
  //   if (response && response.recipient) {
  //     console.log("recipient is: ", response.recipient);
  //     // setAssignedRecipient(response.recipient);
  //   }
  //   setGenerated(true);
  // };

  // const generateSanta = async (groupId) => {
  //   try {
  //     const response = await fetch(`/api/v1/generate_santa/random/${groupId}`, {
  //       method: "POST",
  //       headers: {
  //         "Content-Type": "application/json",
  //       },
  //     });

  //     if (response.ok) {
  //       const result = await response.json();
  //       console.log("GENERATED RESULTS ARE: ", result);
  //       return result;
  //     } else {
  //       console.error("Failed to generate Santa.");
  //       return null;
  //     }
  //   } catch (error) {
  //     console.error("Error generating Santa:", error);
  //     return null;
  //   }
  // };

  useEffect(() => {
    fetchGroups();
    console.log("ViewGroup - Owner ID:", group.ownerId);
    console.log("All group info: ", group);
    console.log("ViewGroup - User ID:", parseInt(params.userId));
    checkSantaPairs();
    fetchUsers();
    console.log("groupId:", typeof params.groupId);
  }, [parseInt(params.groupId)]);

  useEffect(() => {
    newUserName.length > 0 ? fetchFilteredUsers() : fetchUsers();
  }, [newUserName]);

  useEffect(() => {
    console.log(
      "Generated:",
      generated,
      "Assigned Recipient:",
      assignedRecipient
    );

    if (assignedRecipient) {
      console.log("Recipient Name:", assignedRecipient.name);
    }

    if (generated && assignedRecipient) {
      console.log("Fetching GiftList for User ID:", assignedRecipient.userId);
    }
  }, [generated, assignedRecipient]);

  return (
    <div className="ui one column centered equal width grid">
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
                    {user.name}
                  </Button>
                ))}
                {group.ownerId && group.ownerId === parseInt(params.userId) ? (
                  addingUser ? (
                    <div>
                      <Input
                        placeholder="Enter name"
                        value={newUserName} // Change 'nameText' to 'newUserName'
                        onChange={handleNewUserInputChange}
                        onKeyPress={(e) => {
                          if (e.key === "Enter") {
                            handleAddUser();
                          }
                        }}
                      />

                      <div>
                        {filteredUsers.map((user) => (
                          <div
                            key={user.id}
                            onClick={() => handleAddUser(user)}
                          >
                            {user.name}
                          </div>
                        ))}
                      </div>
                    </div>
                  ) : (
                    <Button
                      content="Standard"
                      basic
                      className="button"
                      onClick={handleAddNewUser}
                      color="red"
                    >
                      Add new
                    </Button>
                  )
                ) : null}
              </a>
            </Card.Content>
            {/* {group.ownerId && group.ownerId === parseInt(params.userId) ? (
              <GenerateButton
                generated={generated}
                recipientName={assignedRecipient ? assignedRecipient.name : ""}
              />
            ) : (
              <button className="generate-button">
                Naughty or Nice lists <br />
                are being prepared
              </button>
            )} */}
            <button
              fluid
              className="generate-button"
              size="large"
              onClick={group.ownerId === parseInt(params.userId) ? generated={generated} : null}
              style={{ display: group.ownerId === parseInt(params.userId) || (generated && assignedRecipient) ? "block" : "none" }}
            >
              {group.ownerId === parseInt(params.userId) && !generated
                ? "GENERATE"
                : generated
                  ? `You are secret Santa to ${assignedRecipient ? assignedRecipient.name : assignedRecipient.name}`
                  : null
              }
            </button>


          </Card>

          {console.log(
            "Generated:",
            generated,
            "Assigned Recipient:",
            assignedRecipient
          )}
        </div>
      </div>
      {generated && assignedRecipient && (
        <WishList recipientId={assignedRecipient.userId} />
      )}
    </div>
  );
}
