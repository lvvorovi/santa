// import React, { useState, useEffect } from "react";
// import { Button, Image, Card, Icon, Input } from "semantic-ui-react";
// import { Link, useNavigate, useParams } from "react-router-dom";

// export function GenerateButton({
//   generated,
//   recipientName,
// }) {
//   const params = useParams();

//   const [assignedRecipient, setAssignedRecipient] = useState(null);
//   const [santaPairs, setSantaPairs] = useState({});
//   const [myRecipient, setMyRecipient] = useState(null);
//   const [group, setGroup] = useState({
//     groupId: "",
//     name: "",
//     eventDate: "",
//     budget: "",
//     user: [],
//     gifts: [],
//     ownerId: "",
//     generatedSanta: [],
//   });


//   const generateSanta = async () => {
//     try {
//       const groupId = parseInt(params.groupId);
//       const response = await fetch(`/api/v1/generate_santa/random/${groupId}`, {
//         method: "POST",
//         headers: {
//           "Content-Type": "application/json",
//         },
//       });

//       if (response.ok) {
//         const result = await response.json();
//         console.log(result);
//         // Update state or perform other actions as needed
//       } else {
//         console.error("Failed to generate Santa.");
//       }
//     } catch (error) {
//       console.error("Error generating Santa:", error);
//     }
//   };

//   const fetchMyRecipient = async (userId) => {
//     try {
//       const response = await fetch(
//         `/api/v1/generate_santa/santa_group/${userId}?groupId=${params.groupId}`
//       );

//       if (response.ok) {
//         const generateSanta = await response.json();
//         console.log("generate santa response:", generateSanta);
//         return generateSanta?.recipient?.name || "SOMEONE";
//       } else {
//         console.error("Failed to fetch generateSanta");
//         return "SOMEONE";
//       }
//     } catch (error) {
//       console.error("Error fetching generateSanta:", error);
//       return "SOMEONE";
//     }
//   };

//   useEffect(() => {
//     generateSanta();
//     fetchMyRecipient(params.userId).then((recipient) =>
//       setMyRecipient(recipient)
//     );
//   }, [params.groupId, params.userId]);

//   return (
//     <div>
//       <button
//         fluid
//         className="generate-button"
//         size="large"
//         onClick={generateSanta}
//       >
//         {generated ? `You are secret Santa to ${myRecipient}` : "GENERATE"}
//       </button>
//     </div>
//   );
// }
