// import React, { useState, useEffect } from "react";
// import { Button } from "semantic-ui-react";
// import { useParams } from "react-router-dom";

// export function GenerateButton({ generated, onGenerate }) {
//   const params = useParams();

//   const [myRecipient, setMyRecipient] = useState(null);

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
//         onGenerate(result); // Pass the generated Santa pairs to the parent component
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

//   const handleClick = () => {
//     if (!generated) {
//       generateSanta();
//     }
//   };

//   return (
//     <div>
//       <Button
//         fluid
//         className="generate-button"
//         size="large"
//         onClick={handleClick}
//       >
//         {generated ? `You are secret Santa to ${myRecipient}` : "GENERATE"}
//       </Button>
//     </div>
//   );
// }


//   // return (
//   //   <div>
//   //     <button
//   //       fluid
//   //       className="generate-button"
//   //       size="large"
//   //       onClick={group.ownerId === parseInt(params.userId) ? generated = { generated } : null}
//   //       style={{ display: group.ownerId === parseInt(params.userId) || (generated && assignedRecipient) ? "block" : "none" }}
//   //     >
//   //       {group.ownerId === parseInt(params.userId) && !generated
//   //         ? "GENERATE"
//   //         : generated
//   //           ? `You are secret Santa to ${assignedRecipient ? assignedRecipient.name : assignedRecipient.name}`
//   //           : null
//   //       }
//   //     </button>
//   //   </div>
//   // );

