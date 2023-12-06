import React, { useState, useEffect } from "react";
import { Button, Image, Card, Icon, Input } from "semantic-ui-react";
import { Link, useNavigate, useParams } from "react-router-dom";

export function ViewGroup() {
  const params = useParams();

  const [addingUser, setAddingUser] = useState(false);
  const [newUserName, setNewUserName] = useState("");
  const [filteredUsers, setFilteredUsers] = useState([]);
  const [users, setUsers] = useState([]);
  const [group, setGroup] = useState({
    groupId: "",
    name: "",
    eventDate: "",
    budget: "",
    user: [],
    gifts: [],
    ownerId: "",
    // generatedSanta: [],
  });

  const fetchGroups = async () => {

    fetch("/api/v1/groups/" + parseInt(params.groupId))
      .then((response) => response.json())
      .then(setGroup);
  };

  const fetchUsers = async () => {

    fetch("/api/v1/users")
      .then((response) => response.json())
      .then(setUsers);
  };

  const handleAddNewUser = () => {
    setAddingUser(true);
  };

  const handleNewUserInputChange = (e) => {
    setNewUserName(e.target.value);
  };


  //   const fetchFilteredUsers = async () => {
  //     fetch(`/api/v1/users/name-filter/${nameText}?`)
  //       .then((response) => response.json())
  //       .then((jsonRespone) => setUsers(jsonRespone));
  //   };

  const fetchFilteredUsers = async () => {
    if (newUserName.trim() !== "") {
      try {
        const response = await fetch(
          `/api/v1/users/search?name=${newUserName}`
        );
        if (response.ok) {
          const matchingUsers = await response.json();
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
  
        if (user) {
          const addResponse = await fetch(
            `/api/v1/groups/${parseInt(params.groupId)}/users/${parseInt(user.userId)}/newUsers`,
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



  useEffect(() => {
    fetchGroups();
    fetchUsers();
    console.log("groupId:", typeof params.groupId);
  }, [params.groupId]);

  useEffect(() => {
    newUserName.length > 0 ? fetchFilteredUsers() : fetchUsers();
  }, [newUserName]);

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
                        <div key={user.id} onClick={() => handleAddUser(user)}>
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
                )) : null}
              </a>
            </Card.Content>
            {group.ownerId && group.ownerId === parseInt(params.userId) ? (
                <button className="generate-button" size="large">
                  GENERATE
                </button>
              ) : null}

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